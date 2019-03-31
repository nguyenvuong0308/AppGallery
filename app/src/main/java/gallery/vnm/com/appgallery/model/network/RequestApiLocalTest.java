package gallery.vnm.com.appgallery.model.network;

import android.os.Handler;

import com.google.gson.Gson;

import gallery.vnm.com.appgallery.model.ApiCallBack;
import gallery.vnm.com.appgallery.model.DataImageRequest;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.AlbumRequest;
import gallery.vnm.com.appgallery.model.AlbumResponse;

public class RequestApiLocalTest implements RequestAPI {
    @Override
    public void loadMenus(AlbumRequest albumRequest, ApiCallBack<AlbumResponse> apiCallBack) {
        String jsonMenusTest = "{\n" +
                "  \"menus\": [\n" +
                "    {\n" +
                "      \"key\": \"AnhSanPham\",\n" +
                "      \"name\": \"Ảnh sản phẩm\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"AnhNgoaiTroi\",\n" +
                "      \"name\": \"Ảnh Ngoài Trời\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"key\": \"AnhKhac\",\n" +
                "      \"name\": \"Ảnh Khác\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        apiCallBack.onBeforeRequest();
        new Handler().postDelayed(() -> apiCallBack.onSuccess(new Gson().fromJson(jsonMenusTest, AlbumResponse.class)), 1000);
    }

    @Override
    public void loadImages(DataImageRequest imageRequest, ApiCallBack<DataImagesResponse> apiCallBack) {
        String jsonMenusTest = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles Love this puzzle. One of my favorite puzzles\",\n" +
                "       \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2019/01/Indien25_2-700x551.jpg\"\n" +
                "      ],\n" +
                "      \"post_type\": \"vuongFull\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"post_type\": \"vuong2x2\",\n" +
                "      \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2019/03/the_wanderer_by_the_sea_nygards_karin_bengtsson_2-700x485.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2019/02/518V0A9930tor15-700x471.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/11/Belonging-II.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/10/small_57564.jpg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"post_type\": \"vuong1+2\",\n" +
                "      \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/08/198316400.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/04/cb2.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/03/h%C3%B6rnan_linkimage.jpg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"post_type\": \"vuong1+3\",\n" +
                "      \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/03/DSCF1569.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2018/01/Tuija-Lindstr%C3%B6m_Bruno-Ehrs.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2017/11/Vernissage_Linkimage_bild.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2017/11/00050_118-1.jpg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"post_type\": \"vuong2x1\",\n" +
                "      \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2017/10/2_9R4A9317_sRGB.jpg\",\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2016/12/artedition-01.png\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"post_type\": \"vuong2x1\",\n" +
                "      \"images\": [\n" +
                "        \"http://artedition.linkimage.com/wp-content/uploads/2016/11/012-08_Venedig-4.jpg\",\n" +
                "        \"https://cdn.xl.thumbs.canstockphoto.com/macro-image-of-dark-red-rose-with-water-droplets-extreme-close-up-with-shallow-dof-macro-image-of-picture_csp55779579.jpg\"\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"message\": \"Love this puzzle. One of my favorite puzzles\",\n" +
                "      \"Video\": \"http://domain.com/image/video.mp4\",\n" +
                "      \"Link_thumb\": \"http://domain.com/image/vuong1.jpeg\",\n" +
                "      \"post_type\": \"1video\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        apiCallBack.onBeforeRequest();
        new Handler().postDelayed(() -> apiCallBack.onSuccess(new Gson().fromJson(jsonMenusTest, DataImagesResponse.class)), 1000);

    }
}
