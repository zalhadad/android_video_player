package fr.enssat.alhadad_ngameni.enrichedvideo;

/**
 * Created by zaid on 23/11/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FilmsAdapter extends ArrayAdapter<Film> {

    //tweets est la liste des models à afficher
    public FilmsAdapter(Context context, List<Film> films) {
        super(context, 0, films);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_item, parent, false);
        }

        FilmViewHolder viewHolder = (FilmViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FilmViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.releaseDate = convertView.findViewById(R.id.releaseDate);
            viewHolder.poster = convertView.findViewById(R.id.poster);
            convertView.setTag(viewHolder);
        }

        Film film = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(film.getTitle());
        viewHolder.releaseDate.setText(film.getReleaseDate());
        viewHolder.poster.setImageBitmap(film.getPosterFile());

        return convertView;
    }

    private class FilmViewHolder {
        public TextView title;
        public ImageView poster;
        public TextView releaseDate;
    }
}
