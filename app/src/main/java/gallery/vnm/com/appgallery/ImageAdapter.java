package gallery.vnm.com.appgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 3/6/2019.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private Context mContext;
    private int mMaxSize = 3;
    private ArrayList<String> mImages;
    private OnItemClick<String> mImageOnclick;
    private OnItemClick<ArrayList<String>> mListImageOnclick;

    public ImageAdapter(Context mContext, ArrayList<String> images) {
        this.mContext = mContext;
        if (images != null) {
            this.mImages = images;
        } else this.mImages = new ArrayList<>();
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        mImages = new ArrayList<>();
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_1, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        Glide.with(mContext).load(mImages.get(position)).apply(new RequestOptions().override(holder.mImageView.getWidth(), holder.mImageView.getHeight())).into( holder.mImageView);
        holder.mImageView.setOnClickListener(v -> {
            if (mImageOnclick != null) {
                mImageOnclick.onClick(mImages.get(position), position);
            }

            if (mListImageOnclick != null) {
                mListImageOnclick.onClick(mImages, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size() > mMaxSize ? mMaxSize : mImages.size();
    }

    public void setMaxSize(int mMaxSize) {
        this.mMaxSize = mMaxSize;
    }

    public void setImageOnClick(OnItemClick<String> mImageOnclick) {
        this.mImageOnclick = mImageOnclick;
    }

    public void setListImageOnClick(OnItemClick<ArrayList<String>> mListImageOnclick) {
        this.mListImageOnclick = mListImageOnclick;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        ImageHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv1x1);
        }
    }

}
