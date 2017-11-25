package movies.flag.pt.moviesapp.http.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jailsoncavalcanti on 16/10/2017.
 */

public class TvSeriesResponse {

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<TvSeries> tvseries = new ArrayList<>();

    @SerializedName("total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public List<TvSeries> getTvSeries() {
        return tvseries;
    }
}
