package gallery.vnm.com.appgallery.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.screen.showpostscreen.ShowImageActivity;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadControl {
    private static final String PATH_FOLDER_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AppGallery";
    private static final int LIMIT_DOWNLOAD = 20;

//    public static void downloadFiles1(Context context, ArrayList<String> listUrl, String nameDir) {
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
//        /*tạo folder với tên image đầu tiên trong list*/
//        if (listUrl != null && !listUrl.isEmpty()) {
//            nameDir += System.currentTimeMillis() + "";
//            File file = new File(PATH_FOLDER_IMAGE + File.separator + nameDir);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//        } else {
//            return;
//        }
//
//        /*Tải toàn bộ image trong list*/
//        for (String imageUrl : listUrl) {
//            int indexLast = imageUrl.lastIndexOf(".");
//            String nameImage = nameDir + "_" + System.currentTimeMillis();
//            nameImage += imageUrl.substring(indexLast);
//            if (downloadManager != null) {
//                Uri uri = Uri.parse(imageUrl);
//                downloadManager.enqueue(new DownloadManager.Request(uri)
//                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
//                                DownloadManager.Request.NETWORK_MOBILE)
//                        .setAllowedOverRoaming(false)
//                        .setTitle(nameImage)
//                        .setDescription("Tải về hình ảnh")
//                        .setDestinationInExternalPublicDir(File.separator + "AppGallery" + File.separator + nameDir,
//                                nameImage));
//            }
//        }
//    }

    public static void downloadFiles(Context context, ArrayList<DataImage.Image> listUrl, String nameDir, OnDownloadLimit onDownloadLimit) {
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
        for (DataImage.Image image : listUrl) {
            int indexLast = image.getUrl().lastIndexOf(".");
            String nameImage = nameDir + "_" + System.currentTimeMillis();
            nameImage += image.getUrl().substring(indexLast);
            if (downloadManager != null) {
                if (isDownload()) {
                    Uri uri = Uri.parse(image.getUrl());
                    downloadManager.enqueue(new DownloadManager.Request(uri)
                            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                    DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(nameImage)
                            .setDescription("Tải về hình ảnh")
                            .setDestinationInExternalPublicDir(File.separator + "AppGallery" + File.separator + nameDir,
                                    nameImage));
                } else {
                    onDownloadLimit.onLimitDownload();
                    return;
                }
            }
        }
    }

    public static void downloadFile(ShowImageActivity context, String url, String nameDir, OnDownloadLimit onDownloadLimit) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        /*tạo folder với tên image đầu tiên trong list*/
        if (!TextUtils.isEmpty(url)) {
            nameDir += System.currentTimeMillis() + "";
            File file = new File(PATH_FOLDER_IMAGE + File.separator + nameDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            return;
        }
        int indexLast = url.lastIndexOf(".");
        String nameImage = nameDir + "_" + System.currentTimeMillis();
        nameImage += url.substring(indexLast);
        if (downloadManager != null) {
            if (isDownload()) {
                Uri uri = Uri.parse(url);
                downloadManager.enqueue(new DownloadManager.Request(uri)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(nameImage)
                        .setDescription("Tải về hình ảnh")
                        .setDestinationInExternalPublicDir(File.separator + "AppGallery" + File.separator + nameDir,
                                nameImage));
            } else {
                onDownloadLimit.onLimitDownload();
            }
        }
    }

    /*kiểm tra xem đã hết lượt download chưa*/
    private static boolean isDownload() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + ".AppGallery";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File fileText = new File(path + File.separator + "limit.txt");
        if (!fileText.exists()) {
            try {
                fileText.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Calendar calendar = Calendar.getInstance();
        String date = String.format("%d%d%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        String textLimit = readFile(fileText);
        if (TextUtils.isEmpty(textLimit)) {
            textLimit = date + " 1";
            writeFile(fileText, textLimit);
            return true;
        } else {
            String texts[] = textLimit.split(" ");
            if (texts[0].equals(date)) {
                int limit = Integer.parseInt(texts[1]);
                if (limit >= LIMIT_DOWNLOAD) {
                    return false;
                } else {
                    limit++;
                    textLimit = date + " " + limit;
                    writeFile(fileText, textLimit);
                    return true;
                }
            } else {
                textLimit = date + " 1";
                writeFile(fileText, textLimit);
                return true;
            }
        }
    }

    private static String readFile(File file) {
        StringBuilder aBuffer = new StringBuilder();
        try {
            FileInputStream fIn = new FileInputStream(file);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer.append(aDataRow);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aBuffer.toString();
    }

    private static boolean writeFile(File file, String text) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(text);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public interface OnDownloadLimit {
        void onLimitDownload();
    }
}
