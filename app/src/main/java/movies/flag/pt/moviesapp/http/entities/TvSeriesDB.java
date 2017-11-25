package movies.flag.pt.moviesapp.http.entities;

import com.orm.SugarRecord;

/**
 * Created by jailsoncavalcanti on 20/10/2017.
 */

public class TvSeriesDB extends SugarRecord {

    String title;
    String description;

    public TvSeriesDB(){

    }

    public TvSeriesDB(TvSeries tvSeries){
        this.title = tvSeries.getOriginalName();
        this.description = tvSeries.getOverview();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
