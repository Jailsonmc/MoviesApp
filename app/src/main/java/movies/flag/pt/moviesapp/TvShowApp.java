package movies.flag.pt.moviesapp;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by jailsoncavalcanti on 27/10/2017.
 */

public class TvShowApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        SugarContext.init(this);
    }
}
