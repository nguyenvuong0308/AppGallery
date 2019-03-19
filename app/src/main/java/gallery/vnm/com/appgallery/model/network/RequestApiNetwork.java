package gallery.vnm.com.appgallery.model.network;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.MenuRequest;
import gallery.vnm.com.appgallery.model.MenusResponse;

public class RequestApiNetwork implements RequestAPI {
    @Override
    public void loadMenus(MenuRequest menuRequest, ApiCallBack<MenusResponse> apiCallBack) {

    }

    @Override
    public void loadImages(DataImageRequest imageRequest, ApiCallBack<DataImagesResponse> apiCallBack) {

    }
}
