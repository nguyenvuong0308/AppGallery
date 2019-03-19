package gallery.vnm.com.appgallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mImagesUrl;

    public ImagePagerAdapter(Context mContext, ArrayList<String> mImagesUrl) {
        this.mContext = mContext;
        this.mImagesUrl = mImagesUrl;
    }

    public ImagePagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        PhotoView imageView = (PhotoView) inflater.inflate(R.layout.item_image_full, container, false);
        Glide.with(mContext).load(mImagesUrl.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setMinimumScale(0.5f);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return mImagesUrl.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}
