package gallery.vnm.com.appgallery.screen.mainscreen.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.utils.OnItemClick;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.screen.common.ImageAdapter;
import gallery.vnm.com.appgallery.screen.common.SpanSizeLookup1x1;
import gallery.vnm.com.appgallery.screen.common.SpanSizeLookup1x2;
import gallery.vnm.com.appgallery.screen.common.SpanSizeLookup1x3;
import gallery.vnm.com.appgallery.screen.common.SpanSizeLookup2x1;
import gallery.vnm.com.appgallery.screen.common.SpanSizeLookup2x2;
import gallery.vnm.com.appgallery.model.TypePost;
import gallery.vnm.com.appgallery.model.Album;
import gallery.vnm.com.appgallery.model.DataImage;

/**
 * Created by nguye on 3/6/2019.
 */

public class ListImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOAD_MORE = 1;
    private static final int TYPE_NORMAL = 0;
    private Context mContext;
    private ArrayList<DataImage> mDataImages;
    private OnItemClick<DataImage> mOnItemClick;
    public boolean isClearOnClick = false;
    private OnItemClick.OnItemClick2<DataImage> mEditOnClick;
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean isLoading = false;
    private ILoadMore loadMore;
    private Album mAlbum;
    private int extend = 1;

    public ListImageAdapter(Context mContext, ArrayList<DataImage> mDataImages) {
        this.mContext = mContext;
        this.mDataImages = mDataImages;
    }

    public ListImageAdapter(Context mContext, @NonNull RecyclerView recyclerView) {
        this.mContext = mContext;
        mDataImages = new ArrayList<>();
        int visibleThreshold = 5;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE) {
            return new LoadMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.item_load_more, parent, false));
        } else {
            return new ListImageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_image, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListImageHolder) {
            ListImageHolder listImageHolder = (ListImageHolder) holder;
            DataImage dataImage = mDataImages.get(position);
            listImageHolder.mTvMessage.setText(dataImage.getMessage());
            listImageHolder.mTvWriterName.setText(TextUtils.isEmpty(dataImage.getWriterName()) ? "Unknown" : dataImage.getWriterName());
            listImageHolder.mTvAlbumName.setText(TextUtils.isEmpty(mAlbum.getAlbumName()) ? "Unknown" : mAlbum.getAlbumName());
            Glide.with(mContext).load(dataImage.getWriterThumb()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    listImageHolder.mIvWriterThumb.setImageResource(R.drawable.ic_noimage);
                    return true;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(listImageHolder.mIvWriterThumb);
            GridLayoutManager layoutManager;
            ImageAdapter imageAdapter = new ImageAdapter(mContext, dataImage.getImages(), dataImage.getPostType());
            switch (dataImage.getPostType()) {
                case TypePost.VUONG2X1:
                    layoutManager = new GridLayoutManager(mContext, 2);
                    imageAdapter.setMaxSize(3);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup2x1());
                    break;
                case TypePost.VUONG1X3:
                    layoutManager = new GridLayoutManager(mContext, 3);
                    imageAdapter.setMaxSize(4);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
                    break;
                case TypePost.VUONG1X2:
                    layoutManager = new GridLayoutManager(mContext, 2);
                    imageAdapter.setMaxSize(3);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup1x2());
                    break;
                case TypePost.VUONG2X2:
                    imageAdapter.setMaxSize(4);
                    layoutManager = new GridLayoutManager(mContext, 2);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup2x2());
                    break;
                case TypePost.VUONGFULL:
                    imageAdapter.setMaxSize(1);
                    layoutManager = new GridLayoutManager(mContext, 1);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                    break;
                case TypePost.POST_TYPE_1_AUTO:
                    imageAdapter.setMaxSize(1);
                    layoutManager = new GridLayoutManager(mContext, 1);
                    layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                    break;
                default:
                    imageAdapter.setMaxSize(2);
                    layoutManager = new GridLayoutManager(mContext, 2);
                    break;

            }
            imageAdapter.setImageOnClick((item, position1) ->{
                if (mOnItemClick != null && !isClearOnClick) {
                    mOnItemClick.onClick(dataImage, position);
                }
            });
            listImageHolder.itemView.setOnClickListener(view -> {
                if (mOnItemClick != null && !isClearOnClick) {
                    mOnItemClick.onClick(dataImage, position);
                }
             });
            listImageHolder.mIvActionMore.setOnClickListener(v -> {
                if (mEditOnClick != null) {
                    mEditOnClick.onClick(dataImage, listImageHolder.mIvActionMore, position);
                }
            });
            listImageHolder.mRcvImage.setAdapter(imageAdapter);
            listImageHolder.mRcvImage.setLayoutManager(layoutManager);
            listImageHolder.mRcvImage.setHasFixedSize(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataImages.size() <= position ? TYPE_LOAD_MORE : TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mDataImages.size() + extend;
    }

    public void addDataImages(ArrayList<DataImage> dataImages) {
        if (dataImages != null) {
            mDataImages.addAll(dataImages);
            notifyDataSetChanged();
            isLoading = false;
            extend = 1;
        } else {
            isLoading = true;
            extend = 0;
            notifyDataSetChanged();
        }
    }

    public void setOnItemClick(OnItemClick<DataImage> mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public void refreshData(ArrayList<DataImage> dataImages) {
        mDataImages.clear();
        if (dataImages != null) {
            mDataImages.addAll(dataImages);
            isLoading = false;
            extend = 1;
        } else {
            isLoading = true;
            extend = 0;
        }
        notifyDataSetChanged();
    }

    public void setEditOnClick(OnItemClick.OnItemClick2<DataImage> mEditOnClick) {
        this.mEditOnClick = mEditOnClick;
    }

    public void updateMessageItem(int position, String messageChange) {
        mDataImages.get(position).setMessage(messageChange);
        notifyItemChanged(position);
    }

    public void setAlbum(Album mAlbum) {
        this.mAlbum = mAlbum;
    }

    public interface ILoadMore {
        void onLoadMore();
    }

    public class ListImageHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;
        private ImageView mIvActionMore;
        private RecyclerView mRcvImage;
        private TextView mTvWriterName;
        private TextView mTvAlbumName;
        private ImageView mIvWriterThumb;

        ListImageHolder(View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.tvMessage);
            mIvActionMore = itemView.findViewById(R.id.ivActionMore);
            mRcvImage = itemView.findViewById(R.id.rcvImages);
            mIvActionMore = itemView.findViewById(R.id.ivActionMore);
            mTvWriterName = itemView.findViewById(R.id.tvWriterName);
            mTvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            mIvWriterThumb = itemView.findViewById(R.id.ivWriterThumb);
        }
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;

        LoadMoreHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
