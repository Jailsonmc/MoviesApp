package movies.flag.pt.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.screens.DetailMovieScreen;

/**
 * Created by jailsoncavalcanti on 14/10/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(@NonNull Context context,
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
        MovieAdapter.ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.movie_item, null);
            viewHolder = new MovieAdapter.ViewHolder();
            viewHolder.text = (TextView) v.findViewById(R.id.movie_item_text);
            v.setTag(viewHolder);
        }else{
            v = convertView;
            viewHolder = (MovieAdapter.ViewHolder) v.getTag();
        }

        final Movie item = getItem(position);

        viewHolder.text.setText(String.valueOf(item.getTitle()));

        v.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailMovieScreen.class);
                intent.putExtra( DetailMovieScreen.DETAILS_MOVIE_TEXT_EXTRA_ITEM, (Parcelable) item );
                getContext().startActivity( intent );
            }
        } );

        return v;
    }

    private class ViewHolder{
        private  TextView text;
    }

}
