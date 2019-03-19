package gallery.vnm.com.appgallery;

import android.support.v7.widget.GridLayoutManager;


/**
 * Created by nguye on 3/6/2019.
 */

public class SpanSizeLookup2x1 extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        if (position == 0 || position == 1) {
            return 1;
        } else {
            return 2;
        }
    }
}
