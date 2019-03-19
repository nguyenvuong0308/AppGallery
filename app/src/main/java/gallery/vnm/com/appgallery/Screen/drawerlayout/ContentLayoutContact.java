package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.ApiException;
import gallery.vnm.com.appgallery.model.DataImage;

public class ContentLayoutContact {
    public interface Presenter {
        void loadListImage(LifecycleOwner owner);
    }

    public interface View {
        void onLoadListImage(ArrayList<DataImage> dataImages);

        void onBeforeLoadListImage();

        void onError(ApiException throwable);
    }
}
