package gallery.vnm.com.appgallery.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;

public class ApiCallBack<T> implements IApiCallBack<T> {
    private MutableLiveData<StatusCallBack> callBackLiveData = new MutableLiveData<>();
    private T mResponseSuccess;
    private Exception mThrowable;

    public ApiCallBack(LifecycleOwner lifecycleOwner, IApiCallBack<T> apiCallBackWithResponse) {
        callBackLiveData.observe(lifecycleOwner, statusCallBack -> {
            if (statusCallBack != null && apiCallBackWithResponse != null) {
                switch (statusCallBack) {
                    case STATUS_BEFORE:
                        apiCallBackWithResponse.onBeforeRequest();
                        break;
                    case STATUS_SUCCESS:
                        apiCallBackWithResponse.onSuccess(mResponseSuccess);
                        break;
                    case STATUS_FAIL:
                        apiCallBackWithResponse.onFail(mThrowable);
                        break;
                }
            }
        });
    }

    @Override
    public void onBeforeRequest() {
        mResponseSuccess = null;
        callBackLiveData.setValue(StatusCallBack.STATUS_BEFORE);
    }

    @Override
    public void onSuccess(T response) {
        mResponseSuccess = response;
        callBackLiveData.setValue(StatusCallBack.STATUS_SUCCESS);
    }

    @Override
    public void onFail(Exception throwable) {
        mThrowable = throwable;
        callBackLiveData.setValue(StatusCallBack.STATUS_FAIL);
    }


    enum StatusCallBack {
        STATUS_BEFORE,
        STATUS_SUCCESS,
        STATUS_FAIL,
    }

}