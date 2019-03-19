package gallery.vnm.com.appgallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataImage {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("images")
    @Expose
    private ArrayList<String> images = null;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("Video")
    @Expose
    private String video;
    @SerializedName("Link_thumb")
    @Expose
    private String linkThumb;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getPostType() {
        return postType;
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

    public String getLinkThumb() {
        return linkThumb;
    }

    public void setLinkThumb(String linkThumb) {
        this.linkThumb = linkThumb;
    }

}
