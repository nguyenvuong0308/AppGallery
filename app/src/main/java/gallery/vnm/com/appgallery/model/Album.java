package gallery.vnm.com.appgallery.model;

public class Album {

    private String albumId;
    private String albumName;
    private EnumFlag flag;
    private String extendData;

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
}