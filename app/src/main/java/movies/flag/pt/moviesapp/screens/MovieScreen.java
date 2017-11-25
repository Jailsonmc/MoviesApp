package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.MovieAdapter;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.GetNowPlayingMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by jailsoncavalcanti on 13/10/2017.
 */

public class MovieScreen extends Screen {

    private ListView movieListView;
    private Button movieObterMais;
    private SwipeRefreshLayout swipeRefreshLayout;

    //private long date;

    ArrayList<Movie> movieItems = new ArrayList<>();

    //MoviesResponse moviesResponse = new MoviesResponse();

    private String page = "1";
    private TextView dateRequest;
    private static SimpleDateFormat dateRequisition;
    private long dateInitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateRequisition = new SimpleDateFormat( "MMM dd,yyy HH:mm:ss" );
        dateInitial = System.currentTimeMillis();

        setContentView(R.layout.movie_screen);
        findViews();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.movie_button_get_more, null);

        movieObterMais = (Button) view.findViewById(R.id.movie_button_get_more_button);

        movieListView.addFooterView(view);

        //date = System.currentTimeMillis();

        addListeners();
        executeRequestMovies();

    }
    private void myUpdateOperation() {
        this.page = "1";
        this.movieItems.clear();

        executeRequestMovies();
    }

    private void addListeners() {
        movieObterMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequestMovies();
            }
        });

        swipeRefreshLayout.setColorSchemeResources( R.color.colorAccent,
                R.color.colorSecondary,
                R.color.colorAccentWine);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        myUpdateOperation();

                    }

                }
        );
    }

    private void findViews() {
        movieListView = (ListView) findViewById(R.id.movie_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.movie_swipe_refresh_id );
        dateRequest = (TextView) findViewById( R.id.movie_button_date_resquest );
    }

    private void executeRequestMovies() {
        // Example to request get now playing movies

        new GetNowPlayingMoviesAsyncTask(this, this.page){

            @Override
            protected void onResponseSuccess(MoviesResponse moviesResponse) {
                DLog.d(tag, "onResponseSuccess " + moviesResponse);
                // Here i can create the adapter :)

                final long dateInt =  System.currentTimeMillis();

                Date resultdate = new Date(dateInt);
                dateRequisition = new SimpleDateFormat( "EEE, MMM dd, yyyy HH:mm:ss" );
                String aux = dateRequisition.format(resultdate);
                dateRequest.setText( aux );

                new Handler().postDelayed( new Runnable(){
                    @Override
                    public void run() {
                        long date2 =  System.currentTimeMillis();
                        SimpleDateFormat newDateRequisition2 = new SimpleDateFormat( "MMM dd,yyy HH:mm:ss" );
                        Date resultdate = new Date(date2);
                        String aux = dateRequisition.format(resultdate);

                        long resultDate2 = date2 - dateInt ;
                        Date resultdateFinal = new Date(resultDate2);
                        String aux2 = dateRequisition.format(resultdateFinal);
                    }
                }, 5000);


                new Handler().postDelayed( new Runnable(){
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing( false );

                    }
                }, 2000);

                //Integer pageAux = moviesResponse.getPage();
                Integer pageTotal = moviesResponse.getTotalPages();

                int pageInt = Integer.parseInt(page);
                pageInt++;
                if(pageInt > pageTotal){
                    pageInt--;
                    movieObterMais.setEnabled(false);
                }
                page = Integer.toString(pageInt);

                movieItems.addAll(moviesResponse.getMovies());
                MovieAdapter adapter = new MovieAdapter(MovieScreen.this, movieItems);
                movieListView.setAdapter(adapter);

                

                //movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                  //  @Override
                    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //  TextView movieItemTitle = (TextView) view.findViewById(R.id.movie_item_text);
                        //TextView movieItemPosterPath = (TextView) view.findViewById(R.id.movie_item_poster_path);
                        //Toast.makeText(getApplicationContext(),"Selected Item Name is "+movieItemTitle.getText()+"Image:"+movieItemPosterPath.getText()+"Image:"+movieItemPosterPath.getText(),Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(MovieScreen.this, DetailTvSeriesScreen.class).putExtra( DetailTvSeriesScreen.DETAILS_TV_SERIES_TEXT_EXTRA_POSTER_PATH,movieItemPosterPath.getText()).putExtra( DetailTvSeriesScreen.DETAILS_TV_SERIES_TEXT_EXTRA_TITLE,movieItemTitle.getText()));
                    //}
                //});

            }

            @Override
            protected void onNetworkError() {
                DLog.d(tag, "onNetworkError ");
                // Here i now that some error occur when processing the request,
                // possible my internet connection if turned off



            }
        }.execute();
    }



}
