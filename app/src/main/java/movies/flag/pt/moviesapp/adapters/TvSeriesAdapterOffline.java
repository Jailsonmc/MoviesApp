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
import movies.flag.pt.moviesapp.http.entities.TvSeriesDB;
import movies.flag.pt.moviesapp.screens.DetailTvSeriesScreen;

/**
 * Created by jailsoncavalcanti on 27/10/2017.
 */

public class TvSeriesAdapterOffline extends ArrayAdapter<TvSeriesDB> {

    public TvSeriesAdapterOffline(@NonNull Context context,
                           @NonNull List<TvSeriesDB> object) {
        super( context, 0, object );
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        Log.d( "LIST", "position = " + position +
                " convertView = " + convertView );

        View v;
        TvSeriesAdapterOffline.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from( getContext() );
            v = inflater.inflate( R.layout.tv_series_item, null );
            viewHolder = new TvSeriesAdapterOffline.ViewHolder();
            viewHolder.text = (TextView) v.findViewById( R.id.tv_series_item_text );
            v.setTag( viewHolder );
        } else {
            v = convertView;
            viewHolder = (TvSeriesAdapterOffline.ViewHolder) v.getTag();
        }

        final TvSeriesDB item = getItem( position );

        viewHolder.text.setText( String.valueOf( item.getTitle() ) );

        v.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailTvSeriesScreen.class);
                //intent.putExtra( DetailTvSeriesScreen.DETAILS_TV_SERIES_TEXT_EXTRA_ITEM, item );
                getContext().startActivity( intent );
            }
        } );

        return v;
    }

    private class ViewHolder {
        private TextView text;
    }

}
