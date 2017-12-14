package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaid on 30/11/17.
 */
public class JSONParse extends AsyncTask<String, Void, List<Film>> {
    public JSONRes parent;
    ProgressDialog progDailog;

    public JSONParse(JSONRes res) {

        parent = res;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog((Context) parent);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected List<Film> doInBackground(String... strings) {
        JSONParser jParser = new JSONParser();
        // Getting JSON from URL
        JSONArray json = jParser.getJSONArrayFromUrl(strings[0]);
        List<Film> films = new ArrayList<>();
        try {
            for (int i = 0; i < json.length(); i++) {
                Log.e("ICI", String.valueOf(i));
                Film tmp = new Film(json.getJSONObject(i));
                tmp.loadPoster();
                films.add(tmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return films;
    }

    @Override
    protected void onPostExecute(List<Film> res) {
        super.onPostExecute(res);
        progDailog.dismiss();
        parent.onResult(res);
    }
}