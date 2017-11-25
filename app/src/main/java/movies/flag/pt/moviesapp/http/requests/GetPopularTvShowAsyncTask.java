package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.TvSeriesResponse;

/**
 * Created by jailsoncavalcanti on 16/10/2017.
 */

public abstract class GetPopularTvShowAsyncTask extends ExecuteRequestAsyncTask<TvSeriesResponse> {

    private static final String PATH = "/tv/popular";
    private static final String LANGUAGE_KEY = "language";


    private String page;

    public GetPopularTvShowAsyncTask(Context context, String page) {
        super(context);
        this.page = page;
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, context.getString( R.string.language ));
        addQueryParam(sb, "page", page);
    }

    @Override
    protected Class<TvSeriesResponse> getResponseEntityClass() {
        return TvSeriesResponse.class;
    }

}
