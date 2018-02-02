package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité affichant l'écran principale chargeant la liste des films.
 *
 * @version 1.0
 * @author zaid
 * @author chaka
 */

public class MainActivity extends AppCompatActivity implements JSONRes {
    final MainActivity activity = this;
    ListView mListView;
    FilmsAdapter adapter;
    List<Film> films = new ArrayList<>();
    //  USE STRINGS FOR THIS
    String apiBase = "http://alhadad.fr/films";

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
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Film film = (Film) adapterView.getItemAtPosition(i);
                AlertDialog.Builder d = new AlertDialog.Builder(activity);
                d.setTitle(film.getTitle());
                d.setMessage(film.getSummary());
                d.show();
                return true;
            }
        });

        new JSONParse(activity).execute(apiBase);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_search:
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onResult(List<Film> films) {
        for (int i = 0; i < films.size(); i++) {
            this.adapter.add(films.get(i));
        }
    }

    @Override
    public void onFilm(Film obj) {
        // not used
    }
}
