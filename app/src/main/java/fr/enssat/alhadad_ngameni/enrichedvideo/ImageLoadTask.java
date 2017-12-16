package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.os.AsyncTask;

/**
 * Created by zaid on 10/12/17.
 */

public class ImageLoadTask extends AsyncTask<Film, Void, Film> {
    public ImagesTask parent;
//ProgressDialog pdLoading;

    public ImageLoadTask(ImagesTask res) {

        parent = res;

        // pdLoading = new ProgressDialog(parent);
    }

    @Override
    protected Film doInBackground(Film... films) {

        Film film = films[0];
        film.loadPoster();
        return film;
    }

    @Override
    protected void onPostExecute(Film res) {
        super.onPostExecute(res);
        parent.onResult(res);
    }
}