package gallery.vnm.com.appgallery.model;

public class DataImageRequest {
    private static final String RANGE_SHEET = "!A2:E";
    private static final int PAGE_SIZE = 5;
    private String mKeyMenu;
    private int mPage;

    public String getKeyMenu() {
        if (mKeyMenu != null && mKeyMenu.isEmpty()) {
            return mKeyMenu;
        } else {
            return mKeyMenu + RANGE_SHEET;
        }
    }

    public void setKeyMenu(String mKeyMenu) {
        this.mKeyMenu = mKeyMenu;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public String getRange() throws Exception {
        if (mKeyMenu == null || mKeyMenu.isEmpty()) {
            throw new Exception("Key menu khong the trong!");
        } else {
            boolean isFirstPage = ((mPage - 1) * PAGE_SIZE) == 0;
            int positionStartPage = ((mPage - 1) * PAGE_SIZE) + 2 + (isFirstPage ? 0 : 1);
            int positionEndPage = (mPage * PAGE_SIZE) + 2;
            return mKeyMenu + "!A" + positionStartPage + ":" + "E" + positionEndPage;
        }
    }


}
