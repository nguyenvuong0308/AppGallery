package gallery.vnm.com.appgallery;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadControl {
    private static final String PATH_FOLDER_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AppGallery";

    public static void downloadFiles(Context context, ArrayList<String> listUrl) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        String nameDir = "";
        /*tạo folder với tên image đầu tiên trong list*/
        if (listUrl != null && !listUrl.isEmpty()) {
            String urlFirst = listUrl.get(0);
            int indexOfStartName = urlFirst.lastIndexOf("/");
            int indexOfEndName = urlFirst.lastIndexOf(".");
            indexOfEndName = indexOfEndName == -1 ? listUrl.get(0).length() : indexOfEndName;
            if (indexOfStartName != -1 && indexOfStartName + 1 < indexOfEndName) {
                nameDir = urlFirst.substring(indexOfStartName + 1, indexOfEndName);
                try {
                    nameDir = URLDecoder.decode(nameDir, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                nameDir = System.currentTimeMillis() + "";
            }
            File file = new File(PATH_FOLDER_IMAGE + File.separator + nameDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            return;
        }

        /*Tải toàn bộ image trong list*/
        for (String imageUrl : listUrl) {
            int indexLast = imageUrl.lastIndexOf("/");
            String nameImage = System.currentTimeMillis() + ".png";
            if (indexLast != -1 && indexLast + 1 < imageUrl.length()) {
                nameImage = imageUrl.substring(indexLast + 1);
                try {
                    nameImage = URLDecoder.decode(nameImage, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            File check = new File(PATH_FOLDER_IMAGE + File.separator + nameDir + File.separator + nameImage);
            if (!check.exists()) {
                if (downloadManager != null) {
                    Uri uri = Uri.parse(imageUrl);
                    downloadManager.enqueue(new DownloadManager.Request(uri)
                            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                    DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(nameImage)
                            .setDescription("Tải về hình ảnh")
                            .setDestinationInExternalPublicDir(File.separator + "AppGallery" + File.separator + nameDir,
                                    nameImage));
                }
            }
        }
    }
}
