package movies.flag.pt.moviesapp.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by jailsoncavalcanti on 17/10/2017.
 */

public class ImageDbEntity extends SugarRecord implements Parcelable{

    public static final String TABLE_NAME = "IMAGE_ENTITY";
    public static final String URL_COLUMN_NAME = "url";
    public static final String FILE_PATH_COLUMN_NAME = "file_path";
    public static final String IMAGE_ALREADY_DOWNLOADED_COLUMN_NAME = "image_already_downloaded";

    private static final String FILE_PATH_THREAD_MONITOR = "filePathThreadMonitor";
    private static final String IMAGE_ALREADY_DOWNLOADED_THREAD_MONITOR = "imageAlreadyDownloadedThreadMonitor";

    String url;
    String imageAlreadyDownloaded;
    String filePath;

    @Ignore
    boolean isDownloading;

    public ImageDbEntity(){
        imageAlreadyDownloaded = "false";
    }

    public ImageDbEntity(String url){
        this.url = url;
        imageAlreadyDownloaded = "false";
    }

    public void setDownloading(boolean downloading) {
        isDownloading = downloading;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public boolean isImageAlreadyDownloaded(){
        return imageAlreadyDownloaded != null && imageAlreadyDownloaded.equals("true");
    }

    public void markImageAlreadyDownloaded(){
        imageAlreadyDownloaded = "true";
    }

    public String getUrl(){
        return url;
    }

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(this.url);
        dest.writeString(this.imageAlreadyDownloaded);
        dest.writeString(this.filePath);
        dest.writeByte(this.isDownloading ? (byte) 1 : (byte) 0);
    }

    protected ImageDbEntity(Parcel in) {
        setId(in.readLong());
        this.url = in.readString();
        this.imageAlreadyDownloaded = in.readString();
        this.filePath = in.readString();
        this.isDownloading = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ImageDbEntity> CREATOR = new Parcelable.Creator<ImageDbEntity>() {
        @Override
        public ImageDbEntity createFromParcel(Parcel source) {
            return new ImageDbEntity(source);
        }

        @Override
        public ImageDbEntity[] newArray(int size) {
            return new ImageDbEntity[size];
        }
    };

}
