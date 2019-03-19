package gallery.vnm.com.appgallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MenusResponse {
    @SerializedName("menus")
    @Expose
    private List<Menu> menus = null;

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}