package gallery.vnm.com.appgallery.model;

public interface IApiCallBack<T> {
    void onBeforeRequest();

    void onSuccess(T response);

    void onFail(Exception throwable);
}