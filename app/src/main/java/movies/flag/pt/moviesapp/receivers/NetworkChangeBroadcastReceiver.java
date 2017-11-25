package movies.flag.pt.moviesapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import movies.flag.pt.moviesapp.helpers.NetworkHelper;
import movies.flag.pt.moviesapp.services.VerifyImagesToDownloadService;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(NetworkHelper.networkIsAvailable(context)){
            Log.d(TAG, "wifi is available");
            Log.d(TAG, "Start VerifyImagesToDownloadService");
            Intent serviceIntent = new Intent(context, VerifyImagesToDownloadService.class);
            context.startService(serviceIntent);
        }
        else{
            Log.d(TAG, "wifi is not available");
        }
    }

}
