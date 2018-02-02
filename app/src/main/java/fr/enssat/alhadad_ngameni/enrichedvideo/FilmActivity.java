package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Activité affichant la vidéo du film et la webView.
 *
 * @author zaid
 * @author chaka
 * @version 1.0
 */

public class FilmActivity extends AppCompatActivity {
    private static final String MAPVIEW_BUNDLE_KEY = "MAPVIEW_BUNDLE_KEY";
    Film film;
    VideoView video;
    Spinner spinner;
    WebView page;
    MediaController mc;
    Thread thread;
    MapView mMapView;
    int currentKeywordsId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        Intent intent = getIntent();
        this.film = (Film) intent.getSerializableExtra("film");

        /*
        * ACTIVITY STYLES
        * */
        setTitle(film.getTitle());
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        video = findViewById(R.id.videoView);
        video.setVideoURI(Uri.parse(film.getVideoUrl()));
        mc = new MediaController(FilmActivity.this);
        video.setMediaController(mc);
        page = findViewById(R.id.webView);
        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Chapter> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, film.getChapters());
        adapter.setDropDownViewResource(R.layout.spinner_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        page.setWebViewClient(new WebViewClient());
        page.getSettings().setJavaScriptEnabled(true);
        page.loadUrl(film.getHomepage());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Chapter chapter = film.getChapters().get(i);
                if (i != film.getChapterIndexByTime(video.getCurrentPosition())) {
                    video.seekTo(chapter.getPosition());
                    video.start();
                }
                page.loadUrl(chapter.getPage());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }


        mMapView = findViewById(R.id.mapview);
        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                double lat;
                double lng;
                String label;
                List<Waypoint> waypoints = film.getWaypoints();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (int i = 0; i < waypoints.size(); i++) {
                    Waypoint w = waypoints.get(i);
                    lat = w.getLat();
                    lng = w.getLng();
                    label = w.getLabel();
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lng))
                            .title(label));
                    builder.include(marker.getPosition());

                }
                //TODO load points == null when map is ready
                // builder.include(new LatLng(48.4262302 , -123.3942419));
                // builder.include(new LatLng(33.113676 , -138.8478852));
                LatLngBounds bounds = builder.build();
                int padding = 40;
                if (bounds.northeast != bounds.southwest) {
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300, 200, padding);
                    googleMap.animateCamera(cu);
                }


                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        double lat = marker.getPosition().latitude;
                        double lng = marker.getPosition().longitude;
                        final Waypoint w = film.getWaypointByLatLng(lat, lng);
                        if (w.getTimestamps().size() == 1) {
                            video.seekTo(w.getTimestamps().get(0));
                            focusedElement("v");
                        } else {
                            //SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                            // String time = df.format(new Date(w.getTimestamps().get(0) / 1000));
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
                            df.setTimeZone(TimeZone.getTimeZone("GMT"));
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(FilmActivity.this);
                            builderSingle.setTitle("Select Time :");

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FilmActivity.this, android.R.layout.select_dialog_singlechoice);
                            for (int t = 0; t < w.getTimestamps().size(); t++) {
                                arrayAdapter.add(df.format(w.getTimestamps().get(t)));
                            }

                            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    video.seekTo(w.getTimestamps().get(which));
                                    focusedElement("v");
                                }
                            });
                            builderSingle.show();
                            // show Dialog more than one
                        }
                        return false;
                    }
                });
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        focusedElement("m");
                    }
                });

            }
        });

        this.thread = new Thread() {

            @Override
            public void run() {
                try {
                    class Index {
                        public int value;

                        public Index() {
                            this.value = 0;
                        }
                    }
                    final Index index = new Index();
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!video.isPlaying()) {
                                    video.seekTo(0);
                                    video.start();
                                }
                                updateKeywords();
                                int current_index = film.getChapterIndexByTime(video.getCurrentPosition());
                                if (index.value != current_index) {
                                    index.value = current_index;
                                    spinner.setSelection(index.value, true);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        /*

        FOCUS
         */

        video.setKeepScreenOn(true);
        LinearLayout vc = findViewById(R.id.videoSideContainer);
        vc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedElement("v");
                return false;
            }
        });
        page.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                focusedElement("p");
                return false;
            }
        });

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mc.setAnchorView(video);
                focusedElement("");
            }
        });

        thread.start();


    }

    private void updateKeywords() {
        int m = 8;
        LinearLayout keywordsContainer = findViewById(R.id.keywordsContainer);
        int id = film.getKeywordsIndexByTime(video.getCurrentPosition());
        if (id != -1 && id != currentKeywordsId && id < film.getKeywordsSize()) {
            keywordsContainer.removeAllViews();
            currentKeywordsId = id;
            final Keywords keywords = film.getKeyword(id);
            int margin = toPixel((keywords.getTitles().size() - 1) * m);
            LinearLayout vc = findViewById(R.id.videoSideContainer);
            int size = (vc.getWidth() - margin) / keywords.getTitles().size();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, LinearLayout.LayoutParams.MATCH_PARENT);
            params.width = size;
            for (int i = 0; i < keywords.getTitles().size(); i++) {
                if (i != keywords.getTitles().size() - 1) {
                    params.rightMargin = toPixel(m);
                } else {
                    params.rightMargin = 0;
                }
                Button btn = new Button(this);
                btn.setId(i);
                final int id_ = btn.getId();
                btn.setText(keywords.getTitles().get(i));
                btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                keywordsContainer.addView(btn, params);
                Button btn1 = findViewById(id_);
                final int index = i;
                btn1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        page.loadUrl(keywords.getUrls().get(index));
                        focusedElement("p");
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.film_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resetView:
                focusedElement("");
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.start();
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.interrupt();
        mMapView.onPause();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
    }

    public void focusedElement(String t) {
        final LinearLayout vc = findViewById(R.id.videoSideContainer);
        final LinearLayout cc = findViewById(R.id.contentSideContainer);
        final LinearLayout.LayoutParams pl = (LinearLayout.LayoutParams) page.getLayoutParams();
        final LinearLayout.LayoutParams ml = (LinearLayout.LayoutParams) page.getLayoutParams();
        int h, w;
        switch (t) {
            case "v":
                video.start();
                if (thread.isInterrupted()) {
                    thread.start();
                }
                h = vc.getLayoutParams().height;
                w = vc.getLayoutParams().width;
                vc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.86f));
                h = cc.getLayoutParams().height;
                w = cc.getLayoutParams().width;
                cc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.11f));
                break;
            case "p":
                thread.interrupt();
                video.pause();
                h = vc.getLayoutParams().height;
                w = vc.getLayoutParams().width;
                vc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.1f));
                h = cc.getLayoutParams().height;
                w = cc.getLayoutParams().width;
                cc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.9f));

                pl.weight = 0.9f;
                ml.weight = 0.1f;
                page.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.9f));
                mMapView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f));
                break;
            case "m":
                thread.interrupt();
                video.pause();
                h = vc.getLayoutParams().height;
                w = vc.getLayoutParams().width;
                vc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.1f));
                h = cc.getLayoutParams().height;
                w = cc.getLayoutParams().width;
                cc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.9f));

                pl.weight = 0.1f;
                ml.weight = 0.9f;
                page.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.1f));
                mMapView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.9f));
                break;
            default:
                h = vc.getLayoutParams().height;
                w = vc.getLayoutParams().width;
                vc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.75f));
                h = cc.getLayoutParams().height;
                w = cc.getLayoutParams().width;
                cc.setLayoutParams(new LinearLayout.LayoutParams(w, h, 0.25f));
                page.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.6f));
                mMapView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        }
        ViewTreeObserver observer = vc.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                video.getLayoutParams().height = vc.getWidth() * 9 / 16;
                HorizontalScrollView kcs = findViewById(R.id.keywordsContainerScroll);
                vc.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                kcs.getLayoutParams().height = vc.getHeight() - (vc.getWidth() * 9 / 16) - spinner.getHeight() - toPixel(24);

            }
        });

    }

    private int toPixel(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);

    }

    private int fromPixel(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp / scale + 0.5f);

    }
}
