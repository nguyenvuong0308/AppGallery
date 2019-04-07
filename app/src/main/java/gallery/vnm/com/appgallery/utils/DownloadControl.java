package gallery.vnm.com.appgallery.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadControl {
    private static final String PATH_FOLDER_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AppGallery";

    public static void downloadFiles(Context context, ArrayList<String> listUrl, String nameDir) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        /*tạo folder với tên image đầu tiên trong list*/
        if (listUrl != null && !listUrl.isEmpty()) {
            nameDir += System.currentTimeMillis() + "";
            File file = new File(PATH_FOLDER_IMAGE + File.separator + nameDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            return;
        }

        /*Tải toàn bộ image trong list*/
        for (String imageUrl : listUrl) {
            int indexLast = imageUrl.lastIndexOf(".");
            String nameImage = nameDir + "_" + System.currentTimeMillis();
            nameImage += imageUrl.substring(indexLast);
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
