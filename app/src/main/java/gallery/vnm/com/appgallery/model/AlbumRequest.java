package gallery.vnm.com.appgallery.model;

public class AlbumRequest {
    private static final String RANGE_SHEET = "Albums!A2:D";
    private String mKey;
    private int mPage;

    public AlbumRequest() {
        this.mKey = RANGE_SHEET;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

}
