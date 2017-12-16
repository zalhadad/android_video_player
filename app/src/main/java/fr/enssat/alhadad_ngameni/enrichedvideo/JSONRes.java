package fr.enssat.alhadad_ngameni.enrichedvideo;

import java.util.List;

/**
 * Created by zaid on 30/11/17.
 */

public interface JSONRes {
    void onResult(List<Film> obj);

    void onFilm(Film obj);

}
