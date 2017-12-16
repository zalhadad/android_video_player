package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;


public class FilmActivity extends AppCompatActivity implements ImagesTask {
    Film film;
    VideoView video;
    Spinner spinner;
    WebView page;
    LinearLayout layout;
    MediaController mc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        Intent intent = getIntent();
        this.film = (Film) intent.getSerializableExtra("film");
        new ImageLoadTask(this).execute(film);
        video = findViewById(R.id.videoView);
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
                video.seekTo(chapter.getPosition());
                video.start();
                page.loadUrl(chapter.getPage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        layout = findViewById(R.id.filmLayout);
        final ViewTreeObserver observer = layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // ORIENTATION_PORTRAIT
                            video.getLayoutParams().width = layout.getWidth();
                            int height = (int) Math.round(0.5625 * video.getLayoutParams().width);
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) video.getLayoutParams();
                            params.height = height - (params.leftMargin);
                        } else {
                            video.getLayoutParams().height = layout.getHeight();
                            int width = (int) Math.round(1.7778 * video.getLayoutParams().height);
                            video.getLayoutParams().width = width;
                        }
                        mc.setAnchorView(video);
                    }
                });

    }

    @Override
    public void onResult(Film obj) {
        TextView title = findViewById(R.id.title);
        title.setText(film.getTitle());
        video.setVideoURI(Uri.parse(film.getVideoUrl()));
        video.start();
    }
}
