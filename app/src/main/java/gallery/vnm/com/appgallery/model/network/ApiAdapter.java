package gallery.vnm.com.appgallery.model.network;

import java.util.ArrayList;
import java.util.List;

import gallery.vnm.com.appgallery.model.Album;
import gallery.vnm.com.appgallery.model.AlbumResponse;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.DataImagesResponse;
import gallery.vnm.com.appgallery.model.EnumFlag;
import gallery.vnm.com.appgallery.model.local.MessageComment;
import io.realm.Realm;

public class ApiAdapter {
    public static DataImagesResponse convertResponseDataSheetToDataImage(List<List<Object>> sheets) {
        DataImagesResponse dataImagesResponse = new DataImagesResponse();
        Realm realm = Realm.getDefaultInstance();
        ArrayList<DataImage> dataImages = new ArrayList<>();
        if (sheets != null) {
            for (List<Object> objects : sheets) {
                DataImage dataImage = new DataImage();
                for (int i = 0; i < objects.size(); i++) {
                    switch (i) {
                        case DataImageColumn.ID:
                            dataImage.setId(objects.get(i).toString());
                            break;
                        case DataImageColumn.TEXT_CLIENT_ID:
                            dataImage.setTextClientId(objects.get(i).toString());
                            break;
                        case DataImageColumn.WRITER_NAME:
                            dataImage.setWriterName(objects.get(i).toString());
                            break;
                        case DataImageColumn.WRITER_THUMB:
                            dataImage.setWriterThumb(objects.get(i).toString());
                            break;
                        case DataImageColumn.POST_TYPE:
                            dataImage.setPostType(objects.get(i).toString());
                            break;
                        case DataImageColumn.FLAG:
                            dataImage.setFlag(EnumFlag.getValue(objects.get(i).toString()));
                            break;
                        case DataImageColumn.TAG:
                            dataImage.setTag(objects.get(i).toString());
                            break;
                        case DataImageColumn.HINT:
                            dataImage.setHint(objects.get(i).toString());
                            break;
                        case DataImageColumn.VIDEO:
                            dataImage.setVideo(objects.get(i).toString());
                            break;
                        case DataImageColumn.VIDEO_THUMB:
                            dataImage.setVideoThumb(objects.get(i).toString());
                            break;
                        case DataImageColumn.IMAGES:
                            String imageStr = objects.get(i).toString();
                            if (!imageStr.isEmpty()) {
                                String images[] = imageStr.split("\n");
                                dataImage.setImages(images);
                            }
                            break;
                        case DataImageColumn.MESSAGE:
                            dataImage.setMessage(objects.get(i).toString());
                            break;
                        default:
                            break;
                    }
                }
                MessageComment messageComment = realm.where(MessageComment.class).equalTo("textClientId", dataImage.getTextClientId()).findFirst();
                if (messageComment != null) {
                    dataImage.setMessage(messageComment.getMessageComment());
                }
                if (dataImage.getFlag() != EnumFlag.OFF) {
                    dataImages.add(dataImage);
                }
            }
            dataImagesResponse.setData(dataImages);
        }
        return dataImagesResponse;
    }

    public static AlbumResponse convertResponseDataSheetToDataMenu(List<List<Object>> sheets) {
        AlbumResponse response = new AlbumResponse();
        ArrayList<Album> albums = new ArrayList<>();
        if (sheets != null) {
            for (List<Object> objects : sheets) {
                Album album = new Album();
                for (int i = 0; i < objects.size(); i++) {
                    switch (i) {
                        case AlbumColumn.ALBUM_ID:
                            album.setAlbumId(objects.get(i).toString());
                            break;
                        case AlbumColumn.ALBUM_NAME:
                            album.setAlbumName(objects.get(i).toString());
                            break;
                        case AlbumColumn.FLAG:
                            album.setFlag(EnumFlag.getValue(objects.get(i).toString()));
                            break;
                        case AlbumColumn.EXTEND:
                            album.setExtendData(objects.get(i).toString());
                            break;
                        default:
                            break;
                    }
                }
                if (album.getFlag() != EnumFlag.OFF) {
                    albums.add(album);
                }
            }
        }
        response.setAlbums(albums);
        return response;
    }

    interface DataImageColumn {
        int ID = 0;
        int TEXT_CLIENT_ID = 1;
        int WRITER_NAME = 2;
        int WRITER_THUMB = 3;
        int POST_TYPE = 4;
        int FLAG = 5;
        int TAG = 6;
        int HINT = 7;
        int VIDEO = 8;
        int VIDEO_THUMB = 9;
        int IMAGES = 10;
        int MESSAGE = 11;
    }

    interface AlbumColumn {
        int ALBUM_ID = 0;
        int ALBUM_NAME = 1;
        int FLAG = 2;
        int EXTEND = 3;
    }

}
