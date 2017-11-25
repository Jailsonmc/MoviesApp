package movies.flag.pt.moviesapp.screens;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.adapters.TvSeriesAdapter;
import movies.flag.pt.moviesapp.adapters.TvSeriesAdapterOffline;
import movies.flag.pt.moviesapp.http.entities.TvSeries;
import movies.flag.pt.moviesapp.http.entities.TvSeriesDB;
import movies.flag.pt.moviesapp.http.entities.TvSeriesResponse;
import movies.flag.pt.moviesapp.http.requests.GetPopularTvShowAsyncTask;
import movies.flag.pt.moviesapp.utils.DLog;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public class TvSeriesScreen extends Screen {

    static final long ONE_MINUTE = 60000;

    private ListView tvSeriesListView;
    private Button tvSeriesObterMais;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<TvSeries> tvSeriesItems = new ArrayList<>();

    TvSeriesAdapter tvSeriesAdapter;

    private String page = "1";
    private TextView dateRequest;
    private static SimpleDateFormat dateRequisition;
    private long dateInitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        dateRequisition = new SimpleDateFormat( "MMM dd,yyy HH:mm:ss" );
        dateInitial = System.currentTimeMillis();

        setContentView( R.layout.tv_series_screen );
        findViews();

        LayoutInflater layoutInflater = LayoutInflater.from( this );
        View view = layoutInflater.inflate( R.layout.tv_series_button_get_more, null );

        tvSeriesObterMais = (Button) view.findViewById( R.id.tv_series_button_get_more_button );

        tvSeriesListView.addFooterView( view );

        addListeners();
        executeRequestTvSeries();

    }

    private void myUpdateOperation() {
        this.page = "1";
        this.tvSeriesItems.clear();

        executeRequestTvSeries();
    }

    private void addListeners() {
        tvSeriesObterMais.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequestTvSeries();
                //tvSeriesListView.smoothScrollToPosition( tvSeriesAdapter.getCount() - 1 );
            }
        } );


        swipeRefreshLayout.setColorSchemeResources( R.color.colorAccent,
                                                    R.color.colorSecondary,
                                                    R.color.colorAccentWine);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.

                       // swipeRefreshLayout.setRefreshing( true );
                        // The method calls setRefreshing(false) when it's finished.

                        myUpdateOperation();

                    }

                }
        );

    }

    private void findViews() {
        tvSeriesListView = (ListView) findViewById( R.id.tv_series_list_view );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.tv_series_swipe_refresh_id );
        dateRequest = (TextView) findViewById( R.id.tv_series_button_date_resquest );
    }

    private void executeRequestTvSeries() {
        // Example to request get now playing movies

        new GetPopularTvShowAsyncTask( this, this.page ) {

            @Override
            protected void onResponseSuccess(TvSeriesResponse tvSeriesResponse) {
                DLog.d( tag, "onResponseSuccess " + tvSeriesResponse );
                // Here i can create the adapter :)

                final long dateInt =  System.currentTimeMillis();


                //newDateRequisition = new SimpleDateFormat( "MMM dd,yyy HH:mm:ss" );
                //if((dateInt - dateInitial) > 5*60){
                    //String i = null;

                //}
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
                Integer pageTotal = tvSeriesResponse.getTotalPages();

                int pageInt = Integer.parseInt( page );
                pageInt++;
                if (pageInt > pageTotal) {
                    pageInt--;
                    tvSeriesObterMais.setEnabled( false );
                }
                page = Integer.toString( pageInt );

                tvSeriesItems.addAll( tvSeriesResponse.getTvSeries() );
                tvSeriesAdapter = new TvSeriesAdapter( TvSeriesScreen.this, tvSeriesItems );
                tvSeriesListView.setAdapter( tvSeriesAdapter );

                for (int i = 0; i < tvSeriesItems.size(); i++) {
                    TvSeriesDB tvSeriesDB = new TvSeriesDB( tvSeriesItems.get( i ) );
                    tvSeriesDB.save();
                }

                //tvSeriesDBS = (TvSeriesDB) SugarRecord.saveInTx( tvSeriesItems );

                //tvSeriesListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                //  @Override
                //public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  TextView tvSeriesItemTitle = (TextView) view.findViewById( R.id.tv_series_item_text );
                //TextView tvSeriesItemId = (TextView) view.findViewById( R.id.tv_series_item_id );
                //TextView tvSeriesItemPosterPath = (TextView) view.findViewById( R.id.tv_series_item_poster_path );
                //Toast.makeText( getApplicationContext(), "Selected Item Name is " + tvSeriesItemTitle.getText() + ", com id:" + tvSeriesItemId.getText() + "Class:" + TvSeriesScreen.this.getClass(), Toast.LENGTH_LONG ).show();
                //startActivity( new Intent( TvSeriesScreen.this, DetailTvSeriesScreen.class ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_ID, tvSeriesItemId.getText() ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_POSTER_PATH, tvSeriesItemPosterPath.getText() ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_TITLE, tvSeriesItemTitle.getText() ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_PAGE, page ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_POSITION, tvSeriesItemTitle.getVerticalScrollbarPosition() ).putExtra( DetailTvSeriesScreen.DETAILS_TEXT_EXTRA_FROM_PAGE, TvSeriesScreen.this.getClass() ) );
                //}
                //} );

            }

            @Override
            protected void onNetworkError() {
                DLog.d( tag, "onNetworkError " );
                // Here i now that some error occur when processing the request,
                // possible my internet connection if turned off

                List<TvSeriesDB> tvSeriesDBList = SugarRecord.listAll( TvSeriesDB.class );

                TvSeriesAdapterOffline tvSeriesAdapterOffline = new TvSeriesAdapterOffline( TvSeriesScreen.this, tvSeriesDBList );
                tvSeriesListView.setAdapter( tvSeriesAdapterOffline );


            }
        }.execute();
    }

}
