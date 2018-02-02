package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaid on 19/01/18.
 */

class Keywords implements Comparable, Serializable {
    int pos;
    List<String> titles;
    List<String> urls;

    public Keywords(int pos, JSONArray data) {
        this.pos = pos;
        this.titles = new ArrayList<>();
        this.urls = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject tmpObj = data.getJSONObject(i);
                titles.add(tmpObj.getString("title"));
                urls.add(tmpObj.getString("url"));
            }
        } catch (JSONException e) {
            Log.e("KEYWORDS", e.getMessage());
        }
    }

    public int getPos() {
        return pos;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getUrls() {
        return urls;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Keywords k = (Keywords) o;
        return this.getPos() - k.getPos();
    }

}
