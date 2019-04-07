package gallery.vnm.com.appgallery.screen.common;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by nguye on 3/6/2019.
 */

public class SpanSizeLookup1x3 extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanSize(int position) {
        if(position == 0) {
            return 3;
        } else {
            return 1;
        }
    }
}
