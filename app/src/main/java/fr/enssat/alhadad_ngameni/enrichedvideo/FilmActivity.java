package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;


public class FilmActivity extends AppCompatActivity implements ImagesTask {
    Film film;
    VideoView video;
    android.widget.RelativeLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        Intent intent = getIntent();
        this.film = (Film) intent.getSerializableExtra("film");
        new ImageLoadTask(this).execute(film);
        video = findViewById(R.id.videoView);

        Spinner spinner = findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    /*
     @Override
     public void onConfigurationChanged(Configuration newConfig) {
         super.onConfigurationChanged(newConfig);

         // Checks the orientation of the screen
         if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
             video.setSystemUiVisibility(
                     View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                             | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                             | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                             | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                             | View.SYSTEM_UI_FLAG_FULLSCREEN
                             | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

             params.addRule(android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM);
             params.addRule(android.widget.RelativeLayout.ALIGN_PARENT_TOP);
             params.addRule(android.widget.RelativeLayout.ALIGN_PARENT_LEFT);
             params.addRule(android.widget.RelativeLayout.ALIGN_PARENT_RIGHT);
             getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
             video.setLayoutParams(params);
         } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
             video.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

             params.removeRule(android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM);
             params.removeRule(android.widget.RelativeLayout.ALIGN_PARENT_TOP);
             params.removeRule(android.widget.RelativeLayout.ALIGN_PARENT_LEFT);
             params.removeRule(android.widget.RelativeLayout.ALIGN_PARENT_RIGHT);
             getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
           //  video.setLayoutParams(params);
         }
     }
     */
    @Override
    public void onResult(Film obj) {
        TextView title = findViewById(R.id.title);
        title.setText(film.getTitle());
        Log.i("ezd", film.getVideoUrl());
        video.setVideoURI(Uri.parse(film.getVideoUrl()));
        MediaController mc = new MediaController(FilmActivity.this);
        video.setMediaController(mc);
        mc.setAnchorView(video);

        //  DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // params.width =  metrics.widthPixels;
        // params.height = metrics.heightPixels;
        // params.leftMargin = 0;
        // params.topMargin = 0;

        //this.setRequestedOrientation(ActivityIn.SCREEN_ORIENTATION_LANDSCAPE);


        video.start();
    }
}
