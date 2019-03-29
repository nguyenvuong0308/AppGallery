package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.IApiCallBack;
import gallery.vnm.com.appgallery.model.network.RequestAPI;

public class ContentLayoutPresenter implements ContentLayoutContact.Presenter {
    private ContentLayoutContact.View mView;
    private RequestAPI mRequestAPI;
    private String mAlbumName;
    private int mPage = 0;

    public ContentLayoutPresenter(ContentLayoutContact.View mView, RequestAPI mRequestAPI) {
        this.mView = mView;
        this.mRequestAPI = mRequestAPI;
    }

    @Override
    public void refresh(LifecycleOwner owner, String albumName) {
        mAlbumName = albumName;
        mPage = 1;
        mRequestAPI.loadImages(createDataImageRequest(mAlbumName, mPage), new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
            @Override
            public void onBeforeRequest() {
                mView.onBeforeLoadListImage();
            }

            @Override
            public void onSuccess(DataImagesResponse response) {
                mView.onLoadListImage(response.getData());
            }

            @Override
            public void onFail(Exception throwable) {
                mView.onError(throwable);
            }
        }));
    }

    @Override
    public void loadMore(LifecycleOwner owner) {
        if (mAlbumName != null) {
            mPage++;
            mRequestAPI.loadImages(createDataImageRequest(mAlbumName, mPage), new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
                @Override
                public void onBeforeRequest() {
                    mView.onBeforeLoadListImage();
                }

                @Override
                public void onSuccess(DataImagesResponse response) {
                    mView.onLoadMore(response.getData());
                }

                @Override
                public void onFail(Exception throwable) {
                    mView.onError(throwable);
                }
            }));
        }
    }

    private DataImageRequest createDataImageRequest(String albumName, int mPage) {
        DataImageRequest request = new DataImageRequest();
        request.setKeyMenu(albumName);
        request.setPage(mPage);
        return request;
    }
}
