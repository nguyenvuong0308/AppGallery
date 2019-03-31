package gallery.vnm.com.appgallery.model;

import java.util.ArrayList;
import java.util.Arrays;

public class DataImage {

    private String id;
    private String message;
    private ArrayList<String> images = null;
    private String postType;
    private String video;
    private EnumFlag flag;
    private String tag;
    private String hint;
    private String videoThumb;

    private boolean isDownload = false;

    public boolean isDownload() {
        return isDownload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDownload(boolean download) {
        isDownload = download;
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

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setImages(String images[]) {
        if (images != null && images.length > 0) {
            this.images = new ArrayList<>();
            this.images.addAll(Arrays.asList(images));
        }
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
}
