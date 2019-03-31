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
    private String mAlbumID;
    private int mPage = 0;
    private DataImageRequest mDataImageRequest;

    public ContentLayoutPresenter(ContentLayoutContact.View mView, RequestAPI mRequestAPI) {
        this.mView = mView;
        this.mRequestAPI = mRequestAPI;
    }

    @Override
    public void refresh(LifecycleOwner owner, String albumId) {
        mAlbumID = albumId;
        mPage = 1;
        mRequestAPI.loadImages(createDataImageRequest(mAlbumID, mPage), new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
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
        if (mAlbumID != null) {
            mPage++;
            mRequestAPI.loadImages(createDataImageRequest(mAlbumID, mPage), new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
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

    @Override
    public void tryReload(LifecycleOwner owner) {
        if (mDataImageRequest != null) {
            mRequestAPI.loadImages(mDataImageRequest, new ApiCallBack<>(owner, new IApiCallBack<DataImagesResponse>() {
                @Override
                public void onBeforeRequest() {
                    mView.onBeforeLoadListImage();
                }

                @Override
                public void onSuccess(DataImagesResponse response) {
                    mDataImageRequest = null;
                    if (mPage == 1) {
                        mView.onLoadListImage(response.getData());
                    } else {
                        mView.onLoadMore(response.getData());
                    }
                }

                @Override
                public void onFail(Exception throwable) {
                    mView.onError(throwable);
                }
            }));
        }
    }

    private DataImageRequest createDataImageRequest(String albumId, int mPage) {
        DataImageRequest request = new DataImageRequest();
        request.setAlbumId(albumId);
        request.setPage(mPage);
        mDataImageRequest = request;
        return request;
    }
}
