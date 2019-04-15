package gallery.vnm.com.appgallery.screen.common;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.utils.OnItemClick;

public abstract class BaseItemAdapter<T extends RecyclerView.ViewHolder, IT> extends RecyclerView.Adapter<T> {
    protected int mMaxSize = 3;
    protected OnItemClick<IT> mItemOnclick;
    protected OnItemClick<ArrayList<IT>> mListItemOnClick;

    public void setItemOnclick(OnItemClick<IT> mItemOnclick) {
        this.mItemOnclick = mItemOnclick;
    }

    public void setListItemOnClick(OnItemClick<ArrayList<IT>> mListItemOnClick) {
        this.mListItemOnClick = mListItemOnClick;
    }

    public void setMaxSize(int mMaxSize) {
        this.mMaxSize = mMaxSize;
    }
}
