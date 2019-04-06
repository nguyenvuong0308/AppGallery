package gallery.vnm.com.appgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private String albumId;
    private String albumName;
    private EnumFlag flag;
    private String extendData;

    public Album() {
    }

    protected Album(Parcel in) {
        albumId = in.readString();
        albumName = in.readString();
        extendData = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public String getExtendData() {
        return extendData;
    }

    public void setExtendData(String extendData) {
        this.extendData = extendData;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public EnumFlag getFlag() {
        return flag;
    }

    public void setFlag(EnumFlag flag) {
        this.flag = flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(albumId);
        parcel.writeString(albumName);
        parcel.writeString(extendData);
    }
}