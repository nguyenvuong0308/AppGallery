package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.OnItemClick;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.model.Album;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<DrawerLayoutAdapter.MenuHolder> {
    private Context mContext;
    private ArrayList<Album> mAlbums;
    private OnItemClick<Album> mMenuOnItemClick;
    private Album mAlbumSelected;
    private int positionSelected = -1;

    public DrawerLayoutAdapter(Context mContext, ArrayList<Album> mAlbums) {
        this.mContext = mContext;
        this.mAlbums = mAlbums;
    }

    public DrawerLayoutAdapter(Context mContext) {
        this.mContext = mContext;
        mAlbums = new ArrayList<>();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.mTvAlbumName.setText(mAlbums.get(position).getAlbumName());
        if (position == positionSelected) {
//            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.mTvAlbumName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
//            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            holder.mTvAlbumName.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        holder.itemView.setOnClickListener(v -> {
            mAlbumSelected = mAlbums.get(position);
            positionSelected = position;
            if (mMenuOnItemClick != null) mMenuOnItemClick.onClick(mAlbums.get(position), position);
            notifyDataSetChanged();
        });

        if (position == mAlbums.size() -1) {
            holder.mViewBar.setVisibility(View.GONE);
        } else {
            holder.mViewBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public void addMenus(ArrayList<Album> albums) {
        mAlbums.addAll(albums);
        notifyDataSetChanged();
    }

    public Album getMenuSelected() {
        return mAlbumSelected;
    }

    public void setMenuOnItemClick(OnItemClick<Album> mMenuOnItemClick) {
        this.mMenuOnItemClick = mMenuOnItemClick;
    }

    public void setAlbumSelected(Album album) {
        mAlbumSelected = album;
        for(int i= 0; i < mAlbums.size(); i++) {
            if (mAlbumSelected.getAlbumId().equals(mAlbums.get(i).getAlbumId())) {
                positionSelected = i;
                notifyDataSetChanged();
                return;
            }
        }
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        private TextView mTvAlbumName;
        private ImageView mIvAlbumIcon;
        private View mViewBar;

        MenuHolder(View itemView) {
            super(itemView);
            mTvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            mViewBar = itemView.findViewById(R.id.viewBar);
            mIvAlbumIcon = itemView.findViewById(R.id.ivAlbumIcon);
        }
    }
}