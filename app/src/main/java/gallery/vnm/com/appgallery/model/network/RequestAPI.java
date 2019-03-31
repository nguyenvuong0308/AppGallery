package gallery.vnm.com.appgallery.model.network;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.AlbumRequest;
import gallery.vnm.com.appgallery.model.AlbumResponse;

public interface RequestAPI {
    void loadMenus(AlbumRequest albumRequest, ApiCallBack<AlbumResponse> apiCallBack);

    void loadImages(DataImageRequest imageRequest, ApiCallBack<DataImagesResponse> apiCallBack);
}
