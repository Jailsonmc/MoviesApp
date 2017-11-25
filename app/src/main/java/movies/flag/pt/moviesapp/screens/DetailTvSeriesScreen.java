package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import movies.flag.pt.moviesapp.helpers.DownloadsHelper;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.TvSeries;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public class DetailTvSeriesScreen extends Screen {

    public static final String DETAILS_TV_SERIES_TEXT_EXTRA_TITLE = "DetailTitleExtra";
    public static final String DETAILS_TV_SERIES_TEXT_EXTRA_POSTER_PATH = "DetailPosterPathExtra";
    public static final String PATH = "https://image.tmdb.org/t/p/w500";
    public static final String DETAILS_TV_SERIES_TEXT_EXTRA_ITEM = "item";

    private TextView textDetail;
    private ImageView image;
    private ProgressBar progressBar;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.detail_screen );

        findViews();
        addListeners();


        Intent i = getIntent();

        TvSeries serie = i.getParcelableExtra( DETAILS_TV_SERIES_TEXT_EXTRA_ITEM );

        textDetail.setText( serie.getName() );

        description.setText( serie.getOverview() );

        DownloadImageAsyncTask r = new DownloadImageAsyncTask( progressBar, image );
        String url = PATH+serie.getPoster_path();
        r.execute(url);

    }

    private void addListeners() {

    }

    private void findViews() {
        textDetail = (TextView) findViewById( R.id.detail_screen_label );
        image = (ImageView) findViewById( R.id.detail_screen_image );
        progressBar = (ProgressBar) findViewById( R.id.detail_screen_loader );
        description = (TextView) findViewById( R.id.detail_screen_description );
    }

    private static class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private static final String TAG = DownloadImageAsyncTask.class.getSimpleName();

        private ProgressBar progressBar;
        private ImageView imageView;

        public DownloadImageAsyncTask(ProgressBar progressBar,
                                      ImageView imageView) {
            this.imageView = imageView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d( TAG, "onPreExecute() thread id = " + Thread.currentThread().getId() );
            if (progressBar != null)
                progressBar.setVisibility( View.VISIBLE );
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Log.d( TAG, "doInBackground() thread id = " + Thread.currentThread().getId() );

            if (urls == null || urls.length == 0) {
                return null;
            }

            String url = urls[0];

            Bitmap bitmap = DownloadsHelper.downloadImage( url );

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute( bitmap );
            progressBar.setVisibility( View.GONE );
            imageView.setImageBitmap( bitmap );
        }


    }


}
