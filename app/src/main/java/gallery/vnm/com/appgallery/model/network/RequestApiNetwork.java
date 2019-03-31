package gallery.vnm.com.appgallery.model.network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.AlbumRequest;
import gallery.vnm.com.appgallery.model.AlbumResponse;

public class RequestApiNetwork implements RequestAPI {
    private GoogleAccountCredential mCredential;

    public RequestApiNetwork(@NonNull GoogleAccountCredential mCredential) {
        this.mCredential = mCredential;
    }

    @Override
    public void loadMenus(AlbumRequest albumRequest, ApiCallBack<AlbumResponse> apiCallBack) {
        MakeRequestTask makeRequestTask = new MakeRequestTask(mCredential, new SheetCallBack() {
            @Override
            public void onBeforeRequest() {
                apiCallBack.onBeforeRequest();
            }

            @Override
            public void onSuccess(List<List<Object>> response) {
                apiCallBack.onSuccess(ApiAdapter.convertResponseDataSheetToDataMenu(response));
            }

            @Override
            public void onError(Exception e) {
                apiCallBack.onFail(e);
            }
        }, albumRequest.getKey());
        makeRequestTask.execute();
    }

    @Override
    public void loadImages(DataImageRequest imageRequest, ApiCallBack<DataImagesResponse> apiCallBack) {
        MakeRequestTask makeRequestTask = null;
        try {
            makeRequestTask = new MakeRequestTask(mCredential, new SheetCallBack() {
                @Override
                public void onBeforeRequest() {
                    apiCallBack.onBeforeRequest();
                }

                @Override
                public void onSuccess(List<List<Object>> response) {
                    apiCallBack.onSuccess(ApiAdapter.convertResponseDataSheetToDataImage(response));
                }

                @Override
                public void onError(Exception e) {
                    apiCallBack.onFail(e);
                }
            }, imageRequest.getRange());
            makeRequestTask.execute();
        } catch (Exception e) {
            apiCallBack.onFail(e);
        }
    }


    private interface SheetCallBack {
        void onBeforeRequest();

        void onSuccess(List<List<Object>> response);

        void onError(Exception e);
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<List<Object>>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private String mRange;
        private SheetCallBack mSheetCallBack;

        MakeRequestTask(@NonNull GoogleAccountCredential credential, @NonNull SheetCallBack sheetCallBack, @NonNull String range) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Sheets API Android Quickstart")
                    .build();
            mRange = range;
            mSheetCallBack = sheetCallBack;
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<List<Object>> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mSheetCallBack.onError(e);
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
         *
         * @return List of names and majors
         * @throws IOException
         */
        private List<List<Object>> getDataFromApi() throws IOException {
            String spreadsheetId = "1e3U_GcoqpFxaNrpm5WQiGhb4wzNlwzi8QGfIWUzDsyM";
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, mRange)
                    .execute();
            return response.getValues();
        }


        @Override
        protected void onPreExecute() {
            mSheetCallBack.onBeforeRequest();
        }

        @Override
        protected void onPostExecute(List<List<Object>> results) {
            super.onPostExecute(results);
            mSheetCallBack.onSuccess(results);
        }

    }

}
