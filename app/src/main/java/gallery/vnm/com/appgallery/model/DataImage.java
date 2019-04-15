package gallery.vnm.com.appgallery.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DataImage {
    private static final String TAG = "DataImage";
    private String id = "";
    private String textClientId = "";
    private String writerName = "";
    private String writerThumb = "";
    private String message = "";
    private ArrayList<Image> images = new ArrayList<>();
    private String postType = "";
    private EnumFlag flag;
    private String tag = "";
    private String hint = "";
    private Video videoInfo = new Video();
    private ArrayList<Video> videos;

    private boolean isDownload = false;

    public DataImage() {
    }

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

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void setImages(Image images[]) {
        if (images != null && images.length > 0) {
            this.images = new ArrayList<>();
            this.images.addAll(Arrays.asList(images));
        }
    }

    public ArrayList<Video> getVideos() {
        if (videos == null) {
            videos = new ArrayList<>();
            if (videoInfo != null) {
                videos.add(videoInfo);
            }
        }
        return videos;
    }

    public Video getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(Video videoInfo) {
        this.videoInfo = videoInfo;
    }

    public String getPostType() {
        return postType == null ? "" : postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setVideo(String videoUrl) {
        if (!TextUtils.isEmpty(videoUrl)) {
            int indexSpaceLink = videoUrl.indexOf(" ");
            if (indexSpaceLink != -1) {
                String urlYoutube = videoUrl.substring(0, indexSpaceLink);
                videoInfo.setUrlYoutube(urlYoutube);
                videoInfo.setUrlDownload(videoUrl.substring(indexSpaceLink + 1));
                try {
                    String videoId = urlYoutube.split("v=")[1];
                    int ampersandPosition = videoId.indexOf('&');
                    if (ampersandPosition != -1) {
                        videoId = videoId.substring(0, ampersandPosition);
                    }
                    videoInfo.setIdVideoYoutube(videoId);
                } catch (Exception ex) {
                    Log.e(TAG, ex.toString());
                }

            }
        }
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


    public void setVideoThumb(String videoThumb) {
        this.videoInfo.setUrlThumb(videoThumb);
    }

    public static class Image {
        private String url;
        private String description;

        public Image(String url, String description) {
            this.url = url;
            this.description = description;
        }

        public Image(String url) {
            this.url = url;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Video {
        private String urlYoutube;
        private String urlDownload;
        private String urlThumb;
        private String idVideoYoutube;

        public String getIdVideoYoutube() {
            return idVideoYoutube;
        }

        public void setIdVideoYoutube(String idVideoYoutube) {
            this.idVideoYoutube = idVideoYoutube;
        }

        public String getUrlYoutube() {
            return urlYoutube;
        }

        public void setUrlYoutube(String urlYoutube) {
            this.urlYoutube = urlYoutube;
        }

        public String getUrlDownload() {
            return urlDownload;
        }

        public void setUrlDownload(String urlDownload) {
            this.urlDownload = urlDownload;
        }

        public String getUrlThumb() {
            return urlThumb;
        }

        public void setUrlThumb(String urlThumb) {
            this.urlThumb = urlThumb;
        }
    }
}
