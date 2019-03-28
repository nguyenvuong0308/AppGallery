package gallery.vnm.com.appgallery.model.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gallery.vnm.com.appgallery.MainAct;
import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.Menu;
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
