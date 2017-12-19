package fr.enssat.alhadad_ngameni.enrichedvideo;

import java.io.Serializable;

/**
 * Created by Rushman on 12/14/2017.
 */

public class Chapter implements Serializable {
    private static final long serialVersionUID = 1350092832426723535L;
    String title;
    int position;
    String page;

    public Chapter(String title, int position, String page) {
        this.title = title;
        this.position = position;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public String getPage() {
        return page;
    }


    @Override
    public String toString() {

        return getTitle();
    }
}
