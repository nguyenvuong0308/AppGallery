package gallery.vnm.com.appgallery;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by nguye on 3/6/2019.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private Context mContext;
    private String mTypePost;
    private int mMaxSize = 3;
    private ArrayList<String> mImages;
    private OnItemClick<String> mImageOnclick;
    private OnItemClick<ArrayList<String>> mListImageOnclick;

    public ImageAdapter(Context mContext, ArrayList<String> images, String typePost) {
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
            if (mImageOnclick != null) {
                mImageOnclick.onClick(mImages.get(position), position);
            }

            if (mListImageOnclick != null) {
                mListImageOnclick.onClick(mImages, position);
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
                    int width = displayMetrics.widthPixels;
                    layoutParams.height = width;
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
                    Glide.with(mContext).load(mImages.get(position)).into(holder.mImageView);
                }
                break;

        }
        if (!TypePost.POST_TYPE_1_AUTO.equals(mTypePost)) {
            Glide.with(mContext).load(mImages.get(position)).apply(new RequestOptions().override(holder.mImageView.getWidth(), holder.mImageView.getHeight())).into(holder.mImageView);
        }


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
