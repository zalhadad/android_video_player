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

public class ChapitresAdapter extends ArrayAdapter<Chapter> {

    //tweets est la liste des models à afficher
    public ChapitresAdapter(Context context, List<Chapter> chapters) {
        super(context, 0, chapters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        FilmViewHolder viewHolder = (FilmViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FilmViewHolder();
            viewHolder.title = convertView.findViewById(R.id.chapterTitle);
            convertView.setTag(viewHolder);
        }

        Chapter chapter = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(chapter.getTitle());

        return convertView;
    }

    private class FilmViewHolder {
        public TextView title;
    }
}
