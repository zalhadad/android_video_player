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
 * <b>Film est la classe qui représente un film renvoyé par notre API.</b>
 * Un film est caractérisé par les informations suivantes :
 * <ul>
 *     <li>Un identifiant unique.</li>
 *     <li>Un titre.</li>
 *     <li>Une date de sortie.</li>
 *     <li>Une image poster.</li>
 *     <li>Un résumé.</li>
 *     <li>Une page d'accueil.</li>
 *     <li>Une URL de la vidéo.</li>
 *     <li>Une liste de chpitres</li>
 * </ul>
 * @version 1.0
 * @author zaid
 * @author chaka
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

    /**
     * Constructeur Film.
     *
     * <p>
     *     A la construction d'un objet Film, les différents attributs sont remplis
     *     à partir de l'objet JSON retourné par l'API.
     * </p>
     *
     * @param obj l'objet JSON retrouné l'API.
     * @see fr.enssat.alhadad_ngameni.enrichedvideo.JSONParseFilm#doInBackground(String...)
     *
     */
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

    /**
     * Télécharge l'image du poster du Film à partir de son URL.
     *
     * @param imageUrl l'url de l'image.
     *
     * @return Le bitmap de l'image ou null si on n'a pas pu la télécharger.
     */
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

    /**
     * Charge le bitmap téléchargé dans le poster.
     * @see Film#loadImage(String)
     *
     */
    public void loadPoster() {
        this.posterFile = Film.loadImage(this.poster);
    }

    /**
     *
     * Méthode qui retourne l'index du chapitre du Film en fonction de où l'on se trouve dans la vidéo.
     *
     * @param time
     * @return L'index du chapitre du Film.
     */
    public int getCharperIndexByTime(int time) {
        int i = 0;
        int current = 0;
        while (i < chapters.size() && chapters.get(i).getPosition() <= time) {
            current = i;
            i++;
        }
        return current;
    }

    /**
     * Reinitialise le bitmap du poster du Film à null.
     * Necessaire lorsqu'on veut passer le Film à une activité.
     */

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
