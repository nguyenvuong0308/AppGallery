package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.Screen.MainActivity;
import gallery.vnm.com.appgallery.model.ApiException;
import gallery.vnm.com.appgallery.model.Menu;

public class DrawerLayoutContract {
    public interface Presenter {
        void loadMenu(LifecycleOwner owner);

        void tryReload(LifecycleOwner owner);
    }

    public interface View {
        void onLoadMenu(ArrayList<Menu> mMenus);

        void onBeforeLoadMenu();

        void onError(Exception throwable);
    }
}
