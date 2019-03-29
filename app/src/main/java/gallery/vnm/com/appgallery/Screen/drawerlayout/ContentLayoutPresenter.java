package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.ApiException;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.IApiCallBack;
import gallery.vnm.com.appgallery.model.network.RequestAPI;

public class ContentLayoutPresenter implements ContentLayoutContact.Presenter {
    private ContentLayoutContact.View mView;
    private RequestAPI mRequestAPI;
    private ArrayList<DataImage> mDataImages = new ArrayList<>();

    public ContentLayoutPresenter(ContentLayoutContact.View mView, RequestAPI mRequestAPI) {
        this.mView = mView;
        this.mRequestAPI = mRequestAPI;
    }

    @Override
    public void loadListImage(LifecycleOwner owner) {
        mRequestAPI.loadImages(new DataImageRequest(), new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
            @Override
            public void onBeforeRequest() {
                mView.onBeforeLoadListImage();
            }

            @Override
            public void onSuccess(DataImagesResponse response) {
                mDataImages.addAll(response.getData());
                mView.onLoadListImage(mDataImages);
            }

            @Override
            public void onFail(Exception throwable) {
                mView.onError(throwable);
            }
        }));
    }
}
