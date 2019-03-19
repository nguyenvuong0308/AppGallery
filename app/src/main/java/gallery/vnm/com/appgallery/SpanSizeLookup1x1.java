package gallery.vnm.com.appgallery;

import android.support.v7.widget.GridLayoutManager;

class SpanSizeLookup1x1 extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        return 1;
    }
}
