package gallery.vnm.com.appgallery.screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.Album;

public class DrawerLayoutContract {
    public interface Presenter {
        void loadAlbums(LifecycleOwner owner);

        void tryReload(LifecycleOwner owner);
    }

    public interface View {
        void onLoadAlbums(ArrayList<Album> mAlbums);

        void onBeforeLoadMenu();

        void onError(Exception throwable);

        void onSelectedAlbum(Album album);

        void onUpdateRequired(String extendData);
    }
}
