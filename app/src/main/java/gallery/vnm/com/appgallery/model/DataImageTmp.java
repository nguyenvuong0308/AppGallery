package gallery.vnm.com.appgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataImageTmp implements Parcelable {
    private DataImage dataImage;
    private Album album;

    public DataImageTmp(DataImage dataImage, Album album) {
        this.dataImage = dataImage;
        this.album = album;
    }

    private DataImageTmp(Parcel in) {
    }

    public static final Creator<DataImageTmp> CREATOR = new Creator<DataImageTmp>() {
        @Override
        public DataImageTmp createFromParcel(Parcel in) {
            return new DataImageTmp(in);
        }

        @Override
        public DataImageTmp[] newArray(int size) {
            return new DataImageTmp[size];
        }
    };

    public DataImage getDataImage() {
        return dataImage;
    }

    public void setDataImage(DataImage dataImage) {
        this.dataImage = dataImage;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
