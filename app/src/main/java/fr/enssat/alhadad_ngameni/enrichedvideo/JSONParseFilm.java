package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by zaid on 30/11/17.
 */
public class JSONParseFilm extends AsyncTask<String, Void, Film> {
    public JSONRes parent;
    ProgressDialog progDailog;

    public JSONParseFilm(JSONRes res) {

        parent = res;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog((Context) parent);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();
    }

    @Override
    protected Film doInBackground(String... strings) {
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        JSONObject json = jParser.getJSONFromUrl(strings[0]);
        return new Film(json);
    }

    @Override
    protected void onPostExecute(Film res) {
        super.onPostExecute(res);
        progDailog.dismiss();
        parent.onFilm(res);
    }
}