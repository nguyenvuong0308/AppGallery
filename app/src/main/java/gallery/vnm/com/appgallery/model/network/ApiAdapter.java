package gallery.vnm.com.appgallery.model.network;

import java.util.ArrayList;
import java.util.List;

import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.Menu;

public class ApiAdapter {
    public static ArrayList<DataImage> convertResponseDataSheetToDataImage(List<List<Object>> sheets) {
        ArrayList<DataImage> dataImages = new ArrayList<>();
        for (List<Object> objects: sheets) {
            DataImage dataImage = new DataImage();
            for (int i = 0; i < objects.size(); i++) {
                switch (i) {
                    case 0: dataImage.setMessage(objects.get(i).toString());
                    break;
                    case 1: dataImage.setPostType(objects.get(i).toString());
                    break;
                    case 2: dataImage.setVideo(objects.get(i).toString());
                    break;
                    case 3: dataImage.setLinkThumb(objects.get(i).toString());
                    break;
                    case 4: {
                        String imageStr = objects.get(i).toString();
                        if (!imageStr.isEmpty()) {
                            String images[] = imageStr.split("\n");
                            dataImage.setImages(images);
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
            dataImages.add(dataImage);
        }
        return dataImages;
    }

    public static ArrayList<Menu> convertResponseDataSheetToDataMenu(List<List<Object>> sheets) {
        ArrayList<Menu> menus = new ArrayList<>();
        for (List<Object> objects: sheets) {
            Menu menu = new Menu();
            for (int i = 0; i < objects.size(); i++) {
                switch (i) {
                    case 0: menu.setName(objects.get(i).toString());
                        break;
                    default:
                        break;
                }
            }
            menus.add(menu);
        }
        return menus;
    }

}
