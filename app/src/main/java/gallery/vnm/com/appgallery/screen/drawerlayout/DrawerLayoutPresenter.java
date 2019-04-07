package gallery.vnm.com.appgallery.screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.Album;
import gallery.vnm.com.appgallery.model.AlbumRequest;
import gallery.vnm.com.appgallery.model.AlbumResponse;
import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.EnumFlag;
import gallery.vnm.com.appgallery.model.IApiCallBack;
import gallery.vnm.com.appgallery.model.network.RequestAPI;

public class DrawerLayoutPresenter implements DrawerLayoutContract.Presenter {
    private RequestAPI mRequestAPI;
    private DrawerLayoutContract.View mView;
    private ArrayList<Album> mAlbums = new ArrayList<>();
    private AlbumRequest mAlbumRequest;


    public DrawerLayoutPresenter(DrawerLayoutContract.View view, RequestAPI requestAPI) {
        this.mRequestAPI = requestAPI;
        this.mView = view;
    }

    @Override
    public void loadAlbums(LifecycleOwner owner) {
        mRequestAPI.loadMenus(getMenuRequest(), new ApiCallBack<>(owner, new IApiCallBack<AlbumResponse>() {
            @Override
            public void onBeforeRequest() {
                mView.onBeforeLoadMenu();
            }

            @Override
            public void onSuccess(AlbumResponse response) {
                processResponse(response);
            }

            @Override
            public void onFail(Exception throwable) {
                mView.onError(throwable);
            }
        }));
    }

    @Override
    public void tryReload(LifecycleOwner owner) {
        if (mAlbumRequest != null) {
            mRequestAPI.loadMenus(mAlbumRequest, new ApiCallBack<>(owner, new IApiCallBack<AlbumResponse>() {
                @Override
                public void onBeforeRequest() {
                    mView.onBeforeLoadMenu();
                }

                @Override
                public void onSuccess(AlbumResponse response) {
                    processResponse(response);
                }

                @Override
                public void onFail(Exception throwable) {
                    mView.onError(throwable);
                }
            }));
        }
    }

    private void processResponse(AlbumResponse response) {
        mAlbumRequest = null;
        /*case default và case required update*/
        for (int index = response.getAlbums().size() -1; index >= 0; index--) {
            Album album = response.getAlbums().get(index);
            if (album.getFlag() == EnumFlag.UPDATE_REQUIRED) {
                if (album.getExtendData() != null && (album.getExtendData().startsWith("http://") || album.getExtendData().startsWith("https://"))) {
                    mView.onUpdateRequired(album.getExtendData());
                    response.getAlbums().remove(album);
                    break;
                }
            }
        }
        mAlbums.addAll(response.getAlbums());
        mView.onLoadAlbums(mAlbums);
        for (int index = response.getAlbums().size() -1; index >= 0; index--) {
            Album album = response.getAlbums().get(index);
            if (album.getFlag() == EnumFlag.DEFAULT) {
                mView.onSelectedAlbum(album);
                break;
            }
        }
    }

    private AlbumRequest getMenuRequest() {
        AlbumRequest request = new AlbumRequest();
        mAlbumRequest = request;
        return request;
    }
}