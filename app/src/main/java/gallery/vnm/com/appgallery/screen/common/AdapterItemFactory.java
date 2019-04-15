package gallery.vnm.com.appgallery.screen.common;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.TypePost;

public class AdapterItemFactory {
    private BaseItemAdapter baseItemAdapter;
    private GridLayoutManager layoutManager;

    public BaseItemAdapter getBaseItemAdapter() {
        return baseItemAdapter;
    }

    public void setBaseItemAdapter(BaseItemAdapter baseItemAdapter) {
        this.baseItemAdapter = baseItemAdapter;
    }

    public GridLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public AdapterItemFactory(Context context, DataImage dataImage) {
        if (dataImage.getPostType().equals(TypePost.VIDEO)) {
            baseItemAdapter = new VideoAdapter(context, dataImage.getVideos());
        } else {
            baseItemAdapter = new ImageAdapter(context, dataImage.getImages(), dataImage.getPostType());
        }
        switch (dataImage.getPostType()) {
            case TypePost.VUONG2X1:
                layoutManager = new GridLayoutManager(context, 2);
                baseItemAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x1());
                break;
            case TypePost.VUONG1X3:
                layoutManager = new GridLayoutManager(context, 3);
                baseItemAdapter.setMaxSize(4);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
                break;
            case TypePost.VUONG1X2:
                layoutManager = new GridLayoutManager(context, 2);
                baseItemAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x2());
                break;
            case TypePost.VUONG2X2:
                baseItemAdapter.setMaxSize(4);
                layoutManager = new GridLayoutManager(context, 2);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x2());
                break;
            case TypePost.VUONGFULL:
                baseItemAdapter.setMaxSize(1);
                layoutManager = new GridLayoutManager(context, 1);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                break;
            case TypePost.POST_TYPE_1_AUTO:
            case TypePost.VIDEO:
                baseItemAdapter.setMaxSize(1);
                layoutManager = new GridLayoutManager(context, 1);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                break;
            default:
                baseItemAdapter.setMaxSize(2);
                layoutManager = new GridLayoutManager(context, 2);
                break;
        }
    }

}
