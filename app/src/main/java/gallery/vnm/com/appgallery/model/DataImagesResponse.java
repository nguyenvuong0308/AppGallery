package gallery.vnm.com.appgallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataImagesResponse {

    @SerializedName("data")
    @Expose
    private List<DataImage> data = null;

    public List<DataImage> getData() {
        return data;
    }

    public void setData(List<DataImage> data) {
        this.data = data;
    }

}
