package gallery.vnm.com.appgallery;

import android.view.View;

public interface OnItemClick<T> {
    void onClick(T item, int position);
    interface OnItemClick2<T> {
        void onClick(T item, View view, int position);
    }
}



