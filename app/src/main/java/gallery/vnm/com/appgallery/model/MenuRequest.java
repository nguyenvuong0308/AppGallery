package gallery.vnm.com.appgallery.model;

public class MenuRequest {
    private String mKey;
    private int mPage;

    public MenuRequest() {
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
