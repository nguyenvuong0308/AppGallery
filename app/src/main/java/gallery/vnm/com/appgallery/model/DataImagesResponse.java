package gallery.vnm.com.appgallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataImagesResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<DataImage> data = null;

    public ArrayList<DataImage> getData() {
        return data;
    }

    public void setData(ArrayList<DataImage> data) {
        this.data = data;
    }

}
