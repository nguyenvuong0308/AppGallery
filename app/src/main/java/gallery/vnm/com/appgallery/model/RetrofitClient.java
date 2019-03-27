package gallery.vnm.com.appgallery.model;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    private final Map<String, Retrofit> mClients;

    private RetrofitClient() {
        mClients = new HashMap<>();
    }

    public static RetrofitClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private Retrofit getClient(String baseUrl) {
        Retrofit retrofit = mClients.get(baseUrl);
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.build();
            mClients.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    public <T> T getService(String baseUrl, Class<T> service) {
        Retrofit retrofit = getClient(baseUrl);
        return retrofit.create(service);
    }

    private static final class InstanceHolder {

        private static final RetrofitClient INSTANCE = new RetrofitClient();

        private InstanceHolder() {
            //no instance
        }
    }
}
