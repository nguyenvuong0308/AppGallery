package gallery.vnm.com.appgallery.model;

public class DataImageRequest {
    private static final String RANGE_SHEET = "!A2:E";
    private static final int PAGE_SIZE = 5;
    private String mAlbumId;
    private int mPage;

    public String getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(String mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public String getRange() throws Exception {
        if (mAlbumId == null || mAlbumId.isEmpty()) {
            throw new Exception("Album id không thể trống!");
        } else {
            boolean isFirstPage = ((mPage - 1) * PAGE_SIZE) == 0;
            int positionStartPage = ((mPage - 1) * PAGE_SIZE) + 2 + (isFirstPage ? 0 : 1);
            int positionEndPage = (mPage * PAGE_SIZE) + 2;
            return mAlbumId + "!A" + positionStartPage + ":" + "I" + positionEndPage;
        }
    }
}
