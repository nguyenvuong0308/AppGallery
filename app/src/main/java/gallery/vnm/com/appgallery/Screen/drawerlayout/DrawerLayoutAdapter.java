package gallery.vnm.com.appgallery.Screen.drawerlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.OnItemClick;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.model.Menu;

public class DrawerLayoutAdapter extends RecyclerView.Adapter<DrawerLayoutAdapter.MenuHolder> {
    private Context mContext;
    private ArrayList<Menu> mMenus;
    private OnItemClick<Menu> mMenuOnItemClick;

    public DrawerLayoutAdapter(Context mContext, ArrayList<Menu> mMenus) {
        this.mContext = mContext;
        this.mMenus = mMenus;
    }

    public DrawerLayoutAdapter(Context mContext) {
        this.mContext = mContext;
        mMenus = new ArrayList<>();
    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, int position) {
        holder.mTvMenu.setText(mMenus.get(position).getName());
        holder.mTvMenu.setOnClickListener(v -> {
            if (mMenuOnItemClick != null) mMenuOnItemClick.onClick(mMenus.get(position), position);
        });

    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public void addMenus(ArrayList<Menu> menus) {
        mMenus.addAll(menus);
        notifyDataSetChanged();
    }

    public void setMenuOnItemClick(OnItemClick<Menu> mMenuOnItemClick) {
        this.mMenuOnItemClick = mMenuOnItemClick;
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        private TextView mTvMenu;

        MenuHolder(View itemView) {
            super(itemView);
            mTvMenu = (TextView) itemView;
        }
    }
}