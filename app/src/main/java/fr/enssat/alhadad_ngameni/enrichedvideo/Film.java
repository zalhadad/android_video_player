package fr.enssat.alhadad_ngameni.enrichedvideo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaid on 07/12/17.
 */

class Film implements Serializable {
    private static final long serialVersionUID = 1350092881346723535L;
    private int id;
    private String title;
    private String releaseDate;
    private String poster;
    private String summary;
    private String homepage;
    private String videoUrl;
    private List<Chapter> chapters;

    private Bitmap posterFile;

    public Film(JSONObject obj) {
        try {
            this.id = obj.getInt("id");
            this.title = obj.getString("title");
            this.releaseDate = obj.getString("releaseDate");
            this.poster = obj.getString("poster");
            this.summary = obj.getString("summary");
            this.homepage = obj.getString("homepage");
            this.videoUrl = obj.getJSONObject("video").getString("url");
            JSONArray chapters = obj.getJSONArray("chapters");
            this.chapters = new ArrayList<>();
            // this.chapters.add(new Chapter("Début",0,this.getHomepage()));
            for (int i = 0; i < chapters.length(); i++) {
                JSONObject chapter = chapters.getJSONObject(i);
                this.chapters.add(new Chapter(chapter.getString("name"), chapter.getInt("position"), chapter.getString("page")));
            }
        } catch (JSONException e) {
            Log.e("JSON FILM", e.getMessage());
        } catch (Exception e) {
            Log.e("FILM", e.getMessage());
        }
        this.posterFile = null;

    }

    private static Bitmap loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            Log.e("image error", e.toString());
            return null;

        }
    }

    public String getHomepage() {
        return homepage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }


    public Bitmap getPosterFile() {
        return posterFile;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }


    public void loadPoster() {
        this.posterFile = Film.loadImage(this.poster);
    }

    public int getCharperIndexByTime(int time) {
        int i = 0;
        int current = 0;
        while (i < chapters.size() && chapters.get(i).getPosition() <= time) {
            current = i;
            i++;
        }
        return current;
    }

    public void removeMedia() {
        this.posterFile = null;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }
}
