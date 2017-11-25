package movies.flag.pt.moviesapp.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class DownloadsHelper {

    private static final String TAG = DownloadsHelper.class.getSimpleName();

    public static Bitmap downloadImage(String url) {
        Log.d(TAG, "downloadImage with url = " + url);
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        }catch (MalformedURLException e1){
            Log.e("Error url", " marlformed exception = " + e1.toString());
            return null;
        }
        catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return bitmap;
    }

    public static final byte[] downloadImageUrl(String url){
        try {
            InputStream is = new java.net.URL(url).openStream();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[4096]; // buffer size

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            is.close();

            Log.e(TAG, String.format("Download efectuado com sucesso do url = %s", url));

            return buffer.toByteArray();
        }
        catch(IOException e){
            Log.e(TAG, "Excepção: " + e.getMessage());
        }

        return null;
    }

}
