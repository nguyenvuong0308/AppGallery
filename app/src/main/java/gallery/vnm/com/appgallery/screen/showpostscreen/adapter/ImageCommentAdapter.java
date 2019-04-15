package gallery.vnm.com.appgallery.screen.showpostscreen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.utils.OnItemClick;

public class ImageCommentAdapter extends RecyclerView.Adapter<ImageCommentAdapter.ImageCommentHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<DataImage.Image> mImages;
    private OnItemClick<DataImage.Image> mOnItemClick;

    public ImageCommentAdapter(Context mContext, ArrayList<DataImage.Image> mImages) {
        this.mContext = mContext;
        this.mImages = mImages;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ImageCommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ImageCommentHolder(mInflater.inflate(R.layout.item_image_commment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCommentHolder imageCommentHolder, int position) {
        DataImage.Image image = mImages.get(position);
        Glide.with(mContext).load(image.getUrl()).into(imageCommentHolder.mPhotoView);
        imageCommentHolder.mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageCommentHolder.mPhotoView.setMinimumScale(0.5f);

        if (TextUtils.isEmpty(image.getDescription())) {
            imageCommentHolder.mTvDescription.setVisibility(View.GONE);
        } else {
            imageCommentHolder.mTvDescription.setText(image.getDescription());
            imageCommentHolder.mTvDescription.setVisibility(View.VISIBLE);
        }
        imageCommentHolder.mIvDownload.setOnClickListener(view -> {
            if (mOnItemClick != null) {
                mOnItemClick.onClick(image, position);
            }
        });
    }

    public void setOnItemClick(OnItemClick<DataImage.Image> mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ImageCommentHolder extends RecyclerView.ViewHolder {
        private TextView mTvDescription;
        private PhotoView mPhotoView;
        private ImageView mIvDownload;

        ImageCommentHolder(@NonNull View itemView) {
            super(itemView);
            mTvDescription = itemView.findViewById(R.id.tvDescription);
            mPhotoView = itemView.findViewById(R.id.ivPhoto);
            mIvDownload = itemView.findViewById(R.id.ivDownload);
        }
    }
}
