package gallery.vnm.com.appgallery.screen.common;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.TypePost;

/**
 * Created by nguye on 3/6/2019.
 */

public class ImageAdapter extends BaseItemAdapter<ImageAdapter.ImageHolder, DataImage.Image> {
    private Context mContext;
    private String mTypePost;
    private ArrayList<DataImage.Image> mImages;

    public ImageAdapter(Context mContext, ArrayList<DataImage.Image> images, String typePost) {
        this.mContext = mContext;
        this.mTypePost = typePost;
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
        holder.mImageView.setOnClickListener(v -> {
            if (mItemOnclick != null) {
                mItemOnclick.onClick(mImages.get(position), position);
            }

            if (mListItemOnClick != null) {
                mListItemOnClick.onClick(mImages, position);
            }
        });


        switch (mTypePost) {
            case TypePost.VUONG1X3:
                if (position == 0) {
                    ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = displayMetrics.widthPixels;
                    float height = ((float) width) * (2.0f / 3);
                    layoutParams.height = (int) height;
                    holder.mImageView.setLayoutParams(layoutParams);
                } else {
                    ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = displayMetrics.widthPixels;
                    float height = ((float) width) * (1.0f / 3);
                    layoutParams.height = (int) height;
                }
                break;
            case TypePost.VUONGFULL:
                if (position == 0) {
                    ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    layoutParams.height = displayMetrics.widthPixels;
                    holder.mImageView.setLayoutParams(layoutParams);
                }
                break;
            case TypePost.POST_TYPE_1_AUTO:
                if (position == 0) {
                    ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    holder.mImageView.setAdjustViewBounds(true);
                    holder.mImageView.setLayoutParams(layoutParams);
                    Glide.with(mContext).load(mImages.get(position).getUrl()).into(holder.mImageView);
                }
                break;

        }
        if (!TypePost.POST_TYPE_1_AUTO.equals(mTypePost)) {
            Glide.with(mContext).load(mImages.get(position).getUrl()).apply(new RequestOptions().override(holder.mImageView.getWidth(), holder.mImageView.getHeight())).into(holder.mImageView);
        }


    }

    @Override
    public int getItemCount() {
        return mImages.size() > mMaxSize ? mMaxSize : mImages.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        ImageHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv1x1);
        }
    }

}
