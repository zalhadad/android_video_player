package fr.enssat.alhadad_ngameni.enrichedvideo;

/**
 * Created by zaid on 15/12/17.
 */
 /*
public class FilmSearchAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Film> resultList = new ArrayList<>();

    public FilmSearchAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Film getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.film_item, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView releaseDate = convertView.findViewById(R.id.releaseDate);
        ImageView poster = convertView.findViewById(R.id.poster);

        Film film = getItem(position);

        title.setText(film.getTitle());
        releaseDate.setText(film.getReleaseDate());
        poster.setImageBitmap(film.getPosterFile());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Film> films = findFilms(mContext, constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = films;
                    filterResults.count = films.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Film>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
    private List<Film> findFilms(Context context, String filmTitle) {
        GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
        return protocol.film(filmTitle);
    }
}

*/
