package gallery.vnm.com.appgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by nguye on 3/6/2019.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomHolder(LayoutInflater.from(mContext).inflate(R.layout.item_1, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CustomHolder) holder).mImageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        CustomHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv1x1);
        }
    }

    public class CustomHolder2 extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public CustomHolder2(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv1x1);
        }
    }
}
