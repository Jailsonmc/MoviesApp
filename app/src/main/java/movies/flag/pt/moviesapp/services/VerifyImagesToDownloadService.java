package movies.flag.pt.moviesapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

import movies.flag.pt.moviesapp.database.entities.ImageDbEntity;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class VerifyImagesToDownloadService extends IntentService {

    private static final String TAG = VerifyImagesToDownloadService.class.getSimpleName();

    public VerifyImagesToDownloadService() {
        super("VerifyImagesToDownloadThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<ImageDbEntity> list = (ArrayList<ImageDbEntity>)
                Select.from(ImageDbEntity.class).
                        where(Condition.
                                prop(ImageDbEntity.IMAGE_ALREADY_DOWNLOADED_COLUMN_NAME)
                                .notEq("true")).list();

        // Outra forma de fazer:
//        ArrayList<ImageEntity> list = (ArrayList<ImageEntity>) ImageEntity.findWithQuery(ImageEntity.class,
//                String.format("Select * from %s where (%s != ?)", ImageEntity.TABLE_NAME,
//                        ImageEntity.IMAGE_ALREADY_DOWNLOADED_COLUMN_NAME), "true");

        if(list.size() > 0) {
            Log.d(TAG, String.format("%s urls to download", list.size()));
            Intent newIntent = new Intent(this, DownloadMultipleImagesService.class);
            newIntent.putParcelableArrayListExtra(DownloadMultipleImagesService.IMAGES_LIST_EXTRA, list);
            startService(newIntent);
        }
        else{
            Log.d(TAG, "All urls downloaded");
        }
    }


}
