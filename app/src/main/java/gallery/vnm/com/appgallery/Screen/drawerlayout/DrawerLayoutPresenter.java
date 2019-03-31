package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.arch.lifecycle.LifecycleOwner;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.ApiException;
import gallery.vnm.com.appgallery.model.IApiCallBack;
import gallery.vnm.com.appgallery.model.Menu;
import gallery.vnm.com.appgallery.model.MenuRequest;
import gallery.vnm.com.appgallery.model.MenusResponse;
import gallery.vnm.com.appgallery.model.network.RequestAPI;

public class DrawerLayoutPresenter implements DrawerLayoutContract.Presenter {
    private RequestAPI mRequestAPI;
    private DrawerLayoutContract.View mView;
    private ArrayList<Menu> mMenus = new ArrayList<>();
    private MenuRequest mMenuRequest;


    public DrawerLayoutPresenter(DrawerLayoutContract.View view, RequestAPI requestAPI) {
        this.mRequestAPI = requestAPI;
        this.mView = view;
    }

    @Override
    public void loadMenu(LifecycleOwner owner) {
        mRequestAPI.loadMenus(getMenuRequest(), new ApiCallBack<>(owner, new IApiCallBack<MenusResponse>() {
            @Override
            public void onBeforeRequest() {
                mView.onBeforeLoadMenu();
            }

            @Override
            public void onSuccess(MenusResponse response) {
                mMenuRequest = null;
                mMenus.addAll(response.getMenus());
                mView.onLoadMenu(mMenus);
            }

            @Override
            public void onFail(Exception throwable) {
                mView.onError(throwable);
            }
        }));
    }

    @Override
    public void tryReload(LifecycleOwner owner) {
        if (mMenuRequest != null ) {
            mRequestAPI.loadMenus(mMenuRequest, new ApiCallBack<>(owner, new IApiCallBack<MenusResponse>() {
                @Override
                public void onBeforeRequest() {
                    mView.onBeforeLoadMenu();
                }

                @Override
                public void onSuccess(MenusResponse response) {
                    mMenuRequest = null;
                    mMenus.addAll(response.getMenus());
                    mView.onLoadMenu(mMenus);
                }

                @Override
                public void onFail(Exception throwable) {
                    mView.onError(throwable);
                }
            }));
        }
    }

    private MenuRequest getMenuRequest() {
        MenuRequest request = new MenuRequest();
        request.setKey("Menu Album!A2:A");
        mMenuRequest = request;
        return request;
    }
}
