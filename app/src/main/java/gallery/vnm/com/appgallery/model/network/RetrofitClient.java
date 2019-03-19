package gallery.vnm.com.appgallery.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String BASE_URL = "";
    private static Retrofit sRetrofit;

    public synchronized static Retrofit getRetrofitInstance() {
        if (sRetrofit == null) {
            sRetrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }
}
