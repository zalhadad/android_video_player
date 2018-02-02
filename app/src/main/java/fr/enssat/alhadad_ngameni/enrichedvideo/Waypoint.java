package fr.enssat.alhadad_ngameni.enrichedvideo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zaid on 18/01/18.
 */

public class Waypoint implements Serializable {

    private String label;

    private List timestamps;

    private double lng;
    private double lat;

    public Waypoint(String label, List timestamps, double lat, double lng) {
        this.label = label;
        this.timestamps = timestamps;
        this.lng = lng;
        this.lat = lat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Integer> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List timestamps) {
        this.timestamps = timestamps;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
