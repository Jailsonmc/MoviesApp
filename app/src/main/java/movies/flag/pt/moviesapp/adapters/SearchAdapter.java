package movies.flag.pt.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.screens.DetailMovieScreen;
import movies.flag.pt.moviesapp.screens.DetailTvSeriesScreen;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public class SearchAdapter extends ArrayAdapter<Movie> {

    public SearchAdapter(@NonNull Context context,
                        @NonNull List<Movie> object){
        super(context, 0, object);
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent){
        Log.d("LIST", "position = " + position +
                " convertView = " + convertView);

        View v;
        SearchAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.search_item, null);
            viewHolder = new SearchAdapter.ViewHolder();
            viewHolder.text = (TextView) v.findViewById(R.id.search_item_text);
            v.setTag(viewHolder);
        }else{
            v = convertView;
            viewHolder = (SearchAdapter.ViewHolder) v.getTag();
        }

        final Movie item = getItem(position);

        viewHolder.text.setText(String.valueOf(item.getTitle()));

        v.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailMovieScreen.class);
                intent.putExtra( DetailMovieScreen.DETAILS_MOVIE_TEXT_EXTRA_ITEM, item );
                getContext().startActivity( intent );
            }
        } );

        return v;
    }

    private class ViewHolder{
        private  TextView text;
    }
}
