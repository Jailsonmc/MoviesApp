package movies.flag.pt.moviesapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.SearchAdapter;
import movies.flag.pt.moviesapp.http.entities.Movie;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;
import movies.flag.pt.moviesapp.http.requests.GetSearchMoviesAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public class SearchMovieScreen extends Screen {

    public static final String DETAILS_TEXT_EXTRA = "SearchTextExtra";

    private ListView searchListView;
    private Button searchGetMore;
    private TextView textSearch;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<Movie> movieItems = new ArrayList<>();

    SearchAdapter searchAdapter;

    private String page = "1";
    String textTitle = "";
    private TextView dateRequest;
    private static SimpleDateFormat dateRequisition;
    private long dateInitial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateRequisition = new SimpleDateFormat( "MMM dd,yyy HH:mm:ss" );
        dateInitial = System.currentTimeMillis();

        setContentView(R.layout.search_screen);
        findViews();

        Intent i = getIntent();
        textTitle = i.getStringExtra(DETAILS_TEXT_EXTRA);
        textSearch.setText(textTitle);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.search_button_get_more, null);

        searchGetMore = (Button) view.findViewById(R.id.search_button_get_more_button);

        searchListView.addFooterView(view);

        addListeners();

        executeRequestSearchMovies(textTitle);
    }

    private void myUpdateOperation() {
        this.page = "1";
        this.movieItems.clear();

        executeRequestSearchMovies(textTitle);
    }

    private void addListeners() {
        searchGetMore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequestSearchMovies(textTitle);
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
        searchListView = (ListView) findViewById(R.id.search_list_view);
        textSearch = (TextView) findViewById(R.id.search_text_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.search_swipe_refresh_id );
        dateRequest = (TextView) findViewById( R.id.search_button_date_resquest );
    }

    private void executeRequestSearchMovies(String search) {
        // Example to request get now playing movies

        new GetSearchMoviesAsyncTask(this, this.page, search){

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
                    searchGetMore.setEnabled(false);
                }
                page = Integer.toString(pageInt);

                movieItems.addAll(moviesResponse.getMovies());
                searchAdapter = new SearchAdapter(SearchMovieScreen.this, movieItems);
                searchListView.setAdapter(searchAdapter);

                //searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                  //  @Override
                    //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //  TextView searchItemTitle = (TextView) view.findViewById(R.id.search_item_text);
                        //TextView searchItemPosterPath = (TextView) view.findViewById(R.id.search_item_poster_path);
                        //Toast.makeText(getApplicationContext(),"Selected Item Name is "+searchItemTitle.getText()+"Image:"+searchItemPosterPath.getText()+"Image:"+searchItemPosterPath.getText(),Toast.LENGTH_LONG).show();
                        //startActivity(new Intent(SearchMovieScreen.this, DetailTvSeriesScreen.class).putExtra( DetailTvSeriesScreen.DETAILS_TV_SERIES_TEXT_EXTRA_POSTER_PATH,searchItemPosterPath.getText()).putExtra( DetailTvSeriesScreen.DETAILS_TV_SERIES_TEXT_EXTRA_TITLE,searchItemTitle.getText()));
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
