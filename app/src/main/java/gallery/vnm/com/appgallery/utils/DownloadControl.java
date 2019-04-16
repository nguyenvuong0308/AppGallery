package gallery.vnm.com.appgallery.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.screen.showpostscreen.ShowImageActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DownloadControl {
    private static final String TAG = "DownloadControl";
    private static final String PATH_FOLDER_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AppGallery";
    private static final int LIMIT_DOWNLOAD = 20;

    public static void downloadFiles(Context context, ArrayList<DataImage.Image> listUrl, String nameDir, OnDownloadCallBack onDownloadCallBack) {
        Observable<Long> timeObservable = Observable.create(emitter -> {
            Long time = getTime();
            emitter.onNext(time);
            emitter.onComplete();
        });
        CompositeDisposable disposable = new CompositeDisposable();
        Disposable subscribe = timeObservable.
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(time -> {
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    /*tạo folder với tên image đầu tiên trong list*/
                    if (listUrl != null && !listUrl.isEmpty()) {
                        String mNameDir = nameDir;
                        mNameDir += (nameDir + System.currentTimeMillis() + "");
                        File file = new File(PATH_FOLDER_IMAGE + File.separator + mNameDir);
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
                            if (isDownload(time)) {
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
                                onDownloadCallBack.onLimitDownload();
                                return;
                            }
                        }
                    }
                }, throwable -> {
                    onDownloadCallBack.onDownloadError();
                });
        disposable.add(subscribe);


    }

    public static void downloadFile(ShowImageActivity context, String url, String nameDir, OnDownloadCallBack onDownloadCallBack) {
        Observable<Long> timeObservable = Observable.create(emitter -> {
            Long time = getTime();
            emitter.onNext(time);
            emitter.onComplete();
        });
        CompositeDisposable disposable = new CompositeDisposable();
        Disposable subscribe = timeObservable.
                observeOn(AndroidSchedulers.mainThread()).
                subscribeOn(Schedulers.io()).
                subscribe(time -> {
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    /*tạo folder với tên image đầu tiên trong list*/
                    if (!TextUtils.isEmpty(url)) {
                        String mNameDir = nameDir;
                        mNameDir += System.currentTimeMillis() + "";
                        File file = new File(PATH_FOLDER_IMAGE + File.separator + mNameDir);
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
                        if (isDownload(time)) {
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
                            onDownloadCallBack.onLimitDownload();
                        }
                    }
                }, throwable -> {
                    onDownloadCallBack.onDownloadError();
                });
        disposable.add(subscribe);

    }

    /*kiểm tra xem đã hết lượt download chưa*/
    private static boolean isDownload(Long timeCurrent) {
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
        calendar.setTimeInMillis(timeCurrent);
        String date = String.format("%04d%02d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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

    private static long getTime() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = null;
        doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[]{
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements = doc.select(tags[0]);
        for (String tag : tags) {
            elements = elements.select(tag);
        }
        return Long.parseLong(elements.text() + "000");
    }

    public interface OnDownloadCallBack {
        void onLimitDownload();

        void onDownloadError();
    }
}
