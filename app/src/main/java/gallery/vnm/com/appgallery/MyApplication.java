package gallery.vnm.com.appgallery;

import android.app.Application;

import gallery.vnm.com.appgallery.model.DataImage;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private DataImage dataImageTmp;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("messageComment.realm").schemaVersion(1).build();
        Realm.setDefaultConfiguration(config);
    }

    public DataImage getDataImageTmp() {
        return dataImageTmp;
    }

    public void setDataImageTmp(DataImage dataImageTmp) {
        this.dataImageTmp = dataImageTmp;
    }
}
