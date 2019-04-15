package gallery.vnm.com.appgallery.screen.common;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.model.DataImage;

/**
 * Created by nguye on 3/6/2019.
 */

public class VideoAdapter extends BaseItemAdapter<VideoAdapter.VideoHolder, DataImage.Video> {
    private Context mContext;
    private ArrayList<DataImage.Video> mVideos;

    public VideoAdapter(Context mContext, ArrayList<DataImage.Video> videos) {
        this.mContext = mContext;
        if (videos != null) {
            this.mVideos = videos;
        } else this.mVideos = new ArrayList<>();
    }

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
        mVideos = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        holder.mImageView.setImageResource(R.mipmap.ic_launcher);
        holder.mImageView.setOnClickListener(v -> {
            if (mItemOnclick != null) {
                mItemOnclick.onClick(mVideos.get(position), position);
            }

            if (mListItemOnClick != null) {
                mListItemOnClick.onClick(mVideos, position);
            }
        });

        ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        holder.mImageView.setAdjustViewBounds(true);
        holder.mImageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(mVideos.get(position).getUrlThumb()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mVideos.size() > mMaxSize ? mMaxSize : mVideos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        VideoHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv1x1);
        }
    }

}
