package movies.flag.pt.moviesapp.http.requests;

import android.content.Context;

import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.http.entities.MoviesResponse;

/**
 * Created by jailsoncavalcanti on 15/10/2017.
 */

public abstract class GetSearchMoviesAsyncTask extends ExecuteRequestAsyncTask<MoviesResponse> {

    private static final String PATH = "/search/movie";
    private static final String LANGUAGE_KEY = "language";

    private String page;
    private String query;

    public GetSearchMoviesAsyncTask(Context context, String page, String query) {
        super(context);
        this.page = page;
        this.query = query;
    }
    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected void addQueryParams(StringBuilder sb) {
        addQueryParam(sb, LANGUAGE_KEY, context.getString( R.string.language ));
        addQueryParam(sb, "page", page);
        addQueryParam(sb, "query", query);
    }

    @Override
    protected Class<MoviesResponse> getResponseEntityClass() {
        return MoviesResponse.class;
    }

}
