package movies.flag.pt.moviesapp;

import android.app.Application;
import android.util.Log;

import com.orm.SugarContext;

import movies.flag.pt.moviesapp.helpers.SharedPreferencesHelper;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class FlagApplication extends Application {

    private static final String TAG = FlagApplication.class.getSimpleName();

    private static FlagApplication instance;

    public static FlagApplication getInstance(){
        return instance;
    }

    private int numberOfResumedScreens;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        instance = this;
        SharedPreferencesHelper.init(this);
        SugarContext.init(this);
    }

    public void addOnResumeScreen(){
        numberOfResumedScreens++;
    }

    public void removeOnResumeScreen(){
        numberOfResumedScreens--;
    }

    public boolean applicationIsInBackground(){
        return numberOfResumedScreens == 0;
    }

}
