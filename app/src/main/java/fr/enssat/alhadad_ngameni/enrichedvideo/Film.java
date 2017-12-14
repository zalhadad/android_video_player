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

    private String title;
    private String releaseDate;
    private String poster;
    private String background;
    private String homepage;
    private String videoUrl;
    private int videoDuration;
    private List<Chapter> chapters;

    private Bitmap posterFile;
    private Bitmap backgroundFile;

    public Film(JSONObject obj) {
        try {
            this.title = obj.getString("title");
            this.releaseDate = obj.getString("releaseDate");
            this.poster = obj.getString("poster");
            this.background = obj.getString("background");
            this.homepage = obj.getString("homepage");
            this.videoUrl = obj.getJSONObject("video").getString("url");
            this.videoDuration = obj.getJSONObject("video").getInt("duration");
            JSONArray chapters = obj.getJSONArray("chapters");
            this.chapters = new ArrayList<>();
            for (int i = 0 ; i<chapters.length(); i++) {
                JSONObject chapter = chapters.getJSONObject(i);
                this.chapters.add(new Chapter(chapter.getString("title"),chapter.getDouble("position")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.backgroundFile = null;
        this.posterFile = null;

    }

    public static Bitmap loadImage(String imageUrl) {
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

    public String getBackground() {
        return background;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public Bitmap getPosterFile() {
        return posterFile;
    }

    public Bitmap getBackgroundFile() {
        return backgroundFile;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public void loadPoster() {
        this.posterFile = Film.loadImage(this.poster);
    }

    public void loadBackground() {
        this.backgroundFile = Film.loadImage(this.background);
    }

    public void loadImages() {
        this.loadPoster();
        this.loadBackground();
    }

    public void removeMedia() {
        this.backgroundFile = null;
        this.posterFile = null;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
