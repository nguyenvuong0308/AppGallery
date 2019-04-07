package gallery.vnm.com.appgallery.screen.common;

import android.support.v7.widget.GridLayoutManager;

public class SpanSizeLookup1x1 extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        return 1;
    }
}
