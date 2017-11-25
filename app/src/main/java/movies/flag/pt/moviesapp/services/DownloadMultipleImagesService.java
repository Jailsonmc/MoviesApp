package movies.flag.pt.moviesapp.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;

import movies.flag.pt.moviesapp.FlagApplication;
import movies.flag.pt.moviesapp.R;
import movies.flag.pt.moviesapp.contants.BroadcastActions;
import movies.flag.pt.moviesapp.database.entities.ImageDbEntity;
import movies.flag.pt.moviesapp.helpers.DownloadsHelper;
import movies.flag.pt.moviesapp.helpers.ImagesHelper;
import movies.flag.pt.moviesapp.screens.DetailTvSeriesScreen;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class DownloadMultipleImagesService extends IntentService {

    private static final String TAG = DownloadMultipleImagesService.class.getSimpleName();

    public static final String IMAGES_LIST_EXTRA = "images_list_extra";

    public DownloadMultipleImagesService() {
        super("DownloadFilesThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<ImageDbEntity> entities = intent.getParcelableArrayListExtra(IMAGES_LIST_EXTRA);

        boolean sendBroadcast = false;

        for(ImageDbEntity entity : entities) {
            byte[] imageBytes = DownloadsHelper.downloadImageUrl(entity.getUrl());
            if(imageBytes != null){
                ImagesHelper.saveImageToFile(this, imageBytes, entity);
                // Image downloaded, send broadcast to notify interested on it
                sendBroadcast = true;
            }
        }
        if(sendBroadcast) {
            Log.d(TAG, "sendBroadcast");
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(BroadcastActions.NEW_IMAGES_DOWNLOADED_ACTION);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            if(FlagApplication.getInstance().applicationIsInBackground()) {
                showNotification(entities.size());
            }
        }
    }

    private void showNotification(int size) {
        Log.d(TAG, "showNotification size = " + size);
        Intent intent = new Intent(this, DetailTvSeriesScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification_alert)
                .setContentTitle(getString(R.string.downloads_done))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(getString(R.string.download_services, size));

        int notificationId = 1;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());
    }

}
