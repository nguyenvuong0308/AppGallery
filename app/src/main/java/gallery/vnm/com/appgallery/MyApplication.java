package gallery.vnm.com.appgallery;

import android.app.Application;

import gallery.vnm.com.appgallery.model.DataImageTmp;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private DataImageTmp dataImageTmp;
    private String messageChange;
    private String albumName;
    private int position;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("messageComment.realm").schemaVersion(1).build();
        Realm.setDefaultConfiguration(config);
    }

    public DataImageTmp getDataImageTmp() {
        return dataImageTmp;
    }

    public void setDataImageTmp(DataImageTmp dataImageTmp) {
        this.dataImageTmp = dataImageTmp;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMessageChange() {
        return messageChange;
    }

    public void setMessageChange(String messageChange) {
        this.messageChange = messageChange;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
