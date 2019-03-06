package gallery.vnm.com.appgallery;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by nguye on 3/6/2019.
 */

public class SpanSizeLookup2x2 extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        return 1;
    }
}
