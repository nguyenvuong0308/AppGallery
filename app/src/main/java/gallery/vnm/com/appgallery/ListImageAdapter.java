package gallery.vnm.com.appgallery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.model.DataImage;

/**
 * Created by nguye on 3/6/2019.
 */

public class ListImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<DataImage> mDataImages;
    private OnItemClick<String> mImageOnClick;
    private OnItemClick<ArrayList<String>> mListImageOnClick;
    private int totalItemCount;
    private int lastVisibleItem;
    private boolean isLoading = false;
    private ILoadMore loadMore;
    private static final int TYPE_LOAD_MORE = 1;
    private static final int TYPE_NORMAL = 0;


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
            return new LoadMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.item_load_more, parent,false));
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
            GridLayoutManager layoutManager;
            ImageAdapter imageAdapter = new ImageAdapter(mContext, dataImage.getImages());
            switch (dataImage.getPostType()) {
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
            listImageHolder.mIvEditMessage.setOnClickListener(v -> {
                listImageHolder.mTvMessage.setSingleLine(false);
                listImageHolder.mTvMessage.setEnabled(true);
                listImageHolder.mTbControl2.setVisibility(View.VISIBLE);
            });
            listImageHolder.mIvExpandLess.setOnClickListener(v -> {
                listImageHolder.mTbControl2.setVisibility(View.GONE);
                listImageHolder.mTvMessage.setSingleLine(true);
                listImageHolder.mTvMessage.setEnabled(false);
            });
            listImageHolder.mIvCopy.setOnClickListener(v -> {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(listImageHolder.mTvMessage.getText(), listImageHolder.mTvMessage.getText());
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, "Copy thành công!", Toast.LENGTH_SHORT).show();
                }
            });

            if (!dataImage.isDownload()) {
                listImageHolder.mIvDownloadAlbum.reset();
                Log.d("dataImage", position + ":::");
            } else {
                listImageHolder.mIvDownloadAlbum.end();
            }
            listImageHolder.mIvDownloadAlbum.setDownloadConfig(1000, 0.0, ENDownloadView.DownloadUnit.KB);
            listImageHolder.mIvDownloadAlbum.setOnClickListener(v -> {
                if (!dataImage.isDownload()) {
                    dataImage.setDownload(true);
                    listImageHolder.mIvDownloadAlbum.start();
                    DownloadControl.downloadFiles(mContext, dataImage.getImages());
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

    private int extend = 1;
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

    public void setImageOnClick(OnItemClick<String> mImageOnClick) {
        this.mImageOnClick = mImageOnClick;
    }

    public void setListImageOnclick(OnItemClick<ArrayList<String>> mListImageOnClick) {
        this.mListImageOnClick = mListImageOnClick;
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

    public interface ILoadMore {
        void onLoadMore();
    }

    public class ListImageHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;
        private ImageView mIvEditMessage;
        private ImageView mIvCopy;
        private ImageView mIvExpandLess;
        private ENDownloadView mIvDownloadAlbum;
        private RecyclerView mRcvImage;
        private TableRow mTbControl2;

        ListImageHolder(View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.tvMessage);
            mIvEditMessage = itemView.findViewById(R.id.ivEditMessage);
            mRcvImage = itemView.findViewById(R.id.rcvImages);
            mIvCopy = itemView.findViewById(R.id.ivCopy);
            mIvExpandLess = itemView.findViewById(R.id.ivExpandLess);
            mTbControl2 = itemView.findViewById(R.id.tbControl2);
            mIvDownloadAlbum = itemView.findViewById(R.id.ivDownloadAlbum);
        }
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder{
        private ProgressBar mProgressBar;

        LoadMoreHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
