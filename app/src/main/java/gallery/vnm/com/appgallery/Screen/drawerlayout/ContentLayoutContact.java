package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.Album;
import gallery.vnm.com.appgallery.model.DataImage;

public class ContentLayoutContact {
    public interface Presenter {
        void refresh(LifecycleOwner owner, Album album);

        void loadMore(LifecycleOwner owner);

        void tryReload(LifecycleOwner owner);
    }

    public interface View {
        void onLoadListImage(ArrayList<DataImage> dataImages);

        void onBeforeLoadListImage();

        void onError(Exception throwable);

        void onLoadMore(ArrayList<DataImage> dataImages);

        void onGetAlbumSelected(Album mAlbum);
    }
}
