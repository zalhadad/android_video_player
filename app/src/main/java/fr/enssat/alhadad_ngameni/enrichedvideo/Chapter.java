package fr.enssat.alhadad_ngameni.enrichedvideo;

/**
 * Created by Rushman on 12/14/2017.
 */

public class Chapter {
    String title;
    double position;

    public Chapter(String title, double position) {
        this.title = title;
        this.position = Math.round(position);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }
}
