package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements JSONRes {
    final SearchActivity activity = this;
    ListView mListView;
    SearchView searchBar;
    FilmsAdapter adapter;
    List<Film> films = new ArrayList<>();
    String apiBase = "http://alhadad.fr:9292/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mListView = findViewById(R.id.listViewSearch);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setFocusable(true);
        searchBar.onActionViewExpanded();
        adapter = new FilmsAdapter(SearchActivity.this, films);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Film film = (Film) adapter.getItemAtPosition(position);
                new JSONParseFilm(activity).execute(apiBase + "get/" + String.valueOf(film.getId()));
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
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String title = searchBar.getQuery().toString();
                new JSONParse(activity).execute(apiBase + "search/" + title);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String title = searchBar.getQuery().toString();
                //if(title.length()>2)
                //    new JSONParse(activity).execute(apiBase+"search/"+title);
                return true;
            }
        });

        searchBar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    adapter.clear();
                }
            }
        });
    }

    @Override
    public void onResult(List<Film> films) {
        for (int i = 0; i < films.size(); i++) {
            this.adapter.add(films.get(i));
        }
        if (films.size() == 0) {
            Toast.makeText(this, R.string.no_film, Toast.LENGTH_LONG).show();
        } else {
            searchBar.clearFocus();
        }
    }

    @Override
    public void onFilm(Film obj) {
        Intent i = new Intent(SearchActivity.this, FilmActivity.class);
        if (obj.getId() != 0) {
            i.putExtra("film", obj);
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.no_video, Toast.LENGTH_LONG).show();
        }
    }


}
