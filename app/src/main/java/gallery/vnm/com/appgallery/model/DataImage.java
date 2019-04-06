package gallery.vnm.com.appgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class DataImage implements Parcelable {

    private String id = "";
    private String textClientId = "";
    private String writerName = "";
    private String writerThumb = "";
    private String message = "";
    private ArrayList<String> images = null;
    private String postType = "";
    private String video = "";
    private EnumFlag flag;
    private String tag = "";
    private String hint = "";
    private String videoThumb = "";

    private boolean isDownload = false;

    public DataImage() {
    }

    protected DataImage(Parcel in) {
        id = in.readString();
        textClientId = in.readString();
        writerName = in.readString();
        writerThumb = in.readString();
        message = in.readString();
        images = in.createStringArrayList();
        postType = in.readString();
        video = in.readString();
        tag = in.readString();
        hint = in.readString();
        videoThumb = in.readString();
        isDownload = in.readByte() != 0;
    }

    public static final Creator<DataImage> CREATOR = new Creator<DataImage>() {
        @Override
        public DataImage createFromParcel(Parcel in) {
            return new DataImage(in);
        }

        @Override
        public DataImage[] newArray(int size) {
            return new DataImage[size];
        }
    };

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public String getTextClientId() {
        return textClientId;
    }

    public void setTextClientId(String textClientId) {
        this.textClientId = textClientId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterThumb() {
        return writerThumb;
    }

    public void setWriterThumb(String writerThumb) {
        this.writerThumb = writerThumb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(String images[]) {
        if (images != null && images.length > 0) {
            this.images = new ArrayList<>();
            this.images.addAll(Arrays.asList(images));
        }
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getPostType() {
        return postType == null ? "" : postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public EnumFlag getFlag() {
        return flag;
    }

    public void setFlag(EnumFlag flag) {
        this.flag = flag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getVideoThumb() {
        return videoThumb;
    }

    public void setVideoThumb(String videoThumb) {
        this.videoThumb = videoThumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(textClientId);
        parcel.writeString(writerName);
        parcel.writeString(writerThumb);
        parcel.writeString(message);
        parcel.writeStringList(images);
        parcel.writeString(postType);
        parcel.writeString(video);
        parcel.writeString(tag);
        parcel.writeString(hint);
        parcel.writeString(videoThumb);
        parcel.writeByte((byte) (isDownload ? 1 : 0));
    }
}
