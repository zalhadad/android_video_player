package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements JSONRes {
    final MainActivity activity = this;
    ListView mListView;
    FilmsAdapter adapter;
    List<Film> films = new ArrayList<>();
    String apiBase = "http://alhadad.fr:9292/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.listView);
        adapter = new FilmsAdapter(MainActivity.this, films);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Film film = (Film) adapter.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, FilmActivity.class);
                film.removeMedia();
                i.putExtra("film", film);
                startActivity(i);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });


        new JSONParse(activity).execute(apiBase);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JSONParse(activity).execute(apiBase);
            }
        });
    }

    @Override
    public void onResult(List<Film> films) {
        for (int i = 0; i < films.size(); i++) {
            this.adapter.add(films.get(i));
        }
    }
}
