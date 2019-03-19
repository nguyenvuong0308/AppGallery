package gallery.vnm.com.appgallery.Screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.ImagePagerAdapter;
import gallery.vnm.com.appgallery.R;

public class ShowImageFragment extends Fragment {
    public static final String ARGS_IMAGES_URL = "ARGS_IMAGES_URL";
    public static final String ARGS_POSITION_SELECTED = "ARGS_POSITION_SELECTED";
    private View mRootView;
    private ViewPager mImageViewPager;
    private ImagePagerAdapter mImagePagerAdapter;
    private TabLayout mTabLayout;

    public static ShowImageFragment newInstance(ArrayList<String> imagesUrl, int positionSelected) {
        ShowImageFragment fragment = new ShowImageFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARGS_IMAGES_URL, imagesUrl);
        args.putInt(ARGS_POSITION_SELECTED, positionSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_preview_image, container, false);
        mImageViewPager = mRootView.findViewById(R.id.vpImage);
        mTabLayout = mRootView.findViewById(R.id.tabLayout);
        if (getArguments() != null) {
            ArrayList<String> imagesUrl = getArguments().getStringArrayList(ARGS_IMAGES_URL);
            int positionSelected = getArguments().getInt(ARGS_POSITION_SELECTED);
            mImagePagerAdapter = new ImagePagerAdapter(getContext(), imagesUrl);
            mImageViewPager.setAdapter(mImagePagerAdapter);
            mImageViewPager.setCurrentItem(positionSelected, false);
            mTabLayout.setupWithViewPager(mImageViewPager);

        }
        return mRootView;
    }
}
