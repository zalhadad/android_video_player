package fr.enssat.alhadad_ngameni.enrichedvideo;

import java.util.List;

/**
 * Gère les JSON résultants des différents requêtes fait à l'API.
 * @version 1.0
 * @author zaid
 * @author chaka
 */

public interface JSONRes {
    /**
     * Gère la liste de films renvoyée par l'API.
     * @param obj la liste de films renvoyée par l'API.
     */
    void onResult(List<Film> obj);

    /**
     * Gère le film renvoyée par l'API.
     * @param obj le film renvoyé par l'API.
     */
    void onFilm(Film obj);

}
