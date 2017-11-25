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

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.helpers.DownloadsHelper;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.TvSeries;

/**
 * Created by jailsoncavalcanti on 18/10/2017.
 */

public class DetailMovieScreen extends Screen{

    public static final String DETAILS_MOVIE_PATH = "https://image.tmdb.org/t/p/w500";
    public static final String DETAILS_MOVIE_TEXT_EXTRA_ITEM = "item";

    private TextView textDetailMovie;
    private ImageView imageDetailMovie;
    private ProgressBar progressBarDetailMovie;
    private TextView descriptionDetailMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.detail_movie_screen );

        findViews();
        addListeners();

        Intent i = getIntent();

        Movie movie = i.getParcelableExtra( DETAILS_MOVIE_TEXT_EXTRA_ITEM );

        textDetailMovie.setText( movie.getOriginalTitle() );

        descriptionDetailMovie.setText( movie.getOverview() );

        DetailMovieScreen.DownloadImageAsyncTask r = new DetailMovieScreen.DownloadImageAsyncTask( progressBarDetailMovie, imageDetailMovie );
        String url = DETAILS_MOVIE_PATH+movie.getPosterPath();
        r.execute(url);

    }

    private void addListeners() {

    }

    private void findViews() {
        textDetailMovie = (TextView) findViewById( R.id.detail_movie_screen_label );
        imageDetailMovie = (ImageView) findViewById( R.id.detail_movie_screen_image );
        progressBarDetailMovie = (ProgressBar) findViewById( R.id.detail_movie_screen_loader );
        descriptionDetailMovie = (TextView) findViewById( R.id.detail_movie_screen_description );
    }

    private static class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private static final String TAG = DetailMovieScreen.DownloadImageAsyncTask.class.getSimpleName();

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
