package movies.flag.pt.moviesapp.http.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jailsoncavalcanti on 16/10/2017.
 */

public class TvSeries implements Parcelable{

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("vote_count")
    private String voteCount;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("id")
    private String id;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String poster_path;

    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getMovieId() {
        return id;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( this.originalName );
        dest.writeString( this.name );
        dest.writeString( this.popularity );
        dest.writeString( this.voteCount );
        dest.writeString( this.firstAirDate );
        dest.writeString( this.backdropPath );
        dest.writeString( this.id );
        dest.writeString( this.voteAverage );
        dest.writeString( this.overview );
        dest.writeString( this.poster_path );
    }

    public TvSeries() {
    }

    protected TvSeries(Parcel in) {
        this.originalName = in.readString();
        this.name = in.readString();
        this.popularity = in.readString();
        this.voteCount = in.readString();
        this.firstAirDate = in.readString();
        this.backdropPath = in.readString();
        this.id = in.readString();
        this.voteAverage = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
    }

    public static final Parcelable.Creator<TvSeries> CREATOR = new Parcelable.Creator<TvSeries>() {
        @Override
        public TvSeries createFromParcel(Parcel source) {
            return new TvSeries( source );
        }

        @Override
        public TvSeries[] newArray(int size) {
            return new TvSeries[size];
        }
    };
}
