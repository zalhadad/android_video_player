package fr.enssat.alhadad_ngameni.enrichedvideo;

import java.io.Serializable;

/**
 * <b>Chapitre est la classe qui représente un chapitre d'un film.</b>
 * Un Chapitre est caractérisé par les informations suivantes :
 * <ul>
 *     <li>Un titre.</li>
 *     <li>Une position dans la vidéo.</li>
 *     <li>Une page.</li>
 * </ul>
 * @version 1.0
 * @author zaid
 * @author chaka
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
