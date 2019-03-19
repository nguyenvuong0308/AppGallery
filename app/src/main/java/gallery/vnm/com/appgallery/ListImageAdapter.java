package gallery.vnm.com.appgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.DataImage;

/**
 * Created by nguye on 3/6/2019.
 */

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ListImageHolder> {
    private Context mContext;
    private ArrayList<DataImage> mDataImages;
    private OnItemClick<String> mImageOnClick;
    private OnItemClick<ArrayList<String>> mListImageOnClick;

    public ListImageAdapter(Context mContext, ArrayList<DataImage> mDataImages) {
        this.mContext = mContext;
        this.mDataImages = mDataImages;
    }

    public ListImageAdapter(Context mContext) {
        this.mContext = mContext;
        mDataImages = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListImageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_image, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ListImageHolder holder, int position) {
        holder.mTvMessage.setText(mDataImages.get(position).getMessage());
        GridLayoutManager layoutManager;
        ImageAdapter imageAdapter = new ImageAdapter(mContext, mDataImages.get(position).getImages());
        switch (mDataImages.get(position).getPostType()) {
            case "vuong2x1":
                layoutManager = new GridLayoutManager(mContext, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x1());
                break;
            case "vuong1+3":
                layoutManager = new GridLayoutManager(mContext, 3);
                imageAdapter.setMaxSize(4);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
                break;
            case "vuong1+2":
                layoutManager = new GridLayoutManager(mContext, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x2());
                break;
            case "vuong2x2":
                imageAdapter.setMaxSize(4);
                layoutManager = new GridLayoutManager(mContext, 2);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x2());
                break;
            case "vuongFull":
                imageAdapter.setMaxSize(1);
                layoutManager = new GridLayoutManager(mContext, 1);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                break;
            default:
                imageAdapter.setMaxSize(2);
                layoutManager = new GridLayoutManager(mContext, 2);
                break;

        }
        imageAdapter.setImageOnClick(mImageOnClick);
        imageAdapter.setListImageOnClick(mListImageOnClick);

        holder.mRcvImage.setAdapter(imageAdapter);
        holder.mRcvImage.setLayoutManager(layoutManager);
        holder.mRcvImage.setHasFixedSize(true);
    }

    @Override
    public int getItemCount() {
        return mDataImages.size();
    }

    public void addDataImages(ArrayList<DataImage> dataImages) {
        mDataImages.addAll(dataImages);
        notifyDataSetChanged();
    }

    public void setImageOnClick(OnItemClick<String> mImageOnClick) {
        this.mImageOnClick = mImageOnClick;
    }

    public void setListImageOnclick(OnItemClick<ArrayList<String>> mListImageOnClick) {
        this.mListImageOnClick = mListImageOnClick;
    }

    public class ListImageHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;
        private RecyclerView mRcvImage;

        ListImageHolder(View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.tvMessage);
            mRcvImage = itemView.findViewById(R.id.rcvImages);
        }
    }

}
