package movies.flag.pt.moviesapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import movies.flag.pt.moviesapp.contants.BroadcastActions;
import movies.flag.pt.moviesapp.contants.BroadcastExtras;
import movies.flag.pt.moviesapp.helpers.DownloadsHelper;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class DownloadImageIntentService extends IntentService {

    private static final String TAG = DownloadImageIntentService.class.getSimpleName();

    public static final String IMAGE_URL_EXTRA = "image_url_key";

    public DownloadImageIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent()");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String url = intent.getStringExtra(IMAGE_URL_EXTRA);
        byte[] image = DownloadsHelper.downloadImageUrl(url);

        if(image != null) {
            Log.d(TAG, "download success!");
            Intent broadcastIntent = new Intent(BroadcastActions.BROADCAST_IMAGE_ACTION);
            //broadcastIntent.setAction(Keys.BROADCAST_IMAGE_ACTION);
            broadcastIntent.putExtra(BroadcastExtras.BITMAP_KEY_EXTRA, image);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
        else{
            Log.e(TAG, "download error!");
        }
    }

}
