package gallery.vnm.com.appgallery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
        DataImage dataImage = mDataImages.get(position);
        holder.mTvMessage.setText(dataImage.getMessage());
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
        holder.mIvEditMessage.setOnClickListener(v -> {
            holder.mTvMessage.setSingleLine(false);
            holder.mTvMessage.setEnabled(true);
            holder.mTbControl2.setVisibility(View.VISIBLE);
        });
        holder.mIvExpandLess.setOnClickListener(v -> {
            holder.mTbControl2.setVisibility(View.GONE);
            holder.mTvMessage.setSingleLine(true);
            holder.mTvMessage.setEnabled(false);
        });
        holder.mIvCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(holder.mTvMessage.getText(), holder.mTvMessage.getText());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        if (!dataImage.isDownload()) {
            holder.mIvDownloadAlbum.reset();
            Log.d("dataImage", position + ":::");
        } else {
            holder.mIvDownloadAlbum.end();
        }
        holder.mIvDownloadAlbum.setDownloadConfig(1000, 0.0, ENDownloadView.DownloadUnit.KB);
        holder.mIvDownloadAlbum.setOnClickListener(v -> {
            if (!dataImage.isDownload()) {
                dataImage.setDownload(true);
                holder.mIvDownloadAlbum.start();
                DownloadControl.downloadFiles(mContext, dataImage.getImages());
            }
        });
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

}
