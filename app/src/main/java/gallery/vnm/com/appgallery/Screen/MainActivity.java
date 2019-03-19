package gallery.vnm.com.appgallery.Screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import gallery.vnm.com.appgallery.ListImageAdapter;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.Screen.drawerlayout.ContentLayoutContact;
import gallery.vnm.com.appgallery.Screen.drawerlayout.ContentLayoutPresenter;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutAdapter;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutContract;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutPresenter;
import gallery.vnm.com.appgallery.model.ApiException;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.Menu;
import gallery.vnm.com.appgallery.model.network.RequestApiLocalTest;

/**
 * Created by nguye on 3/6/2019.
 */

public class MainActivity extends AppCompatActivity implements DrawerLayoutContract.View, ContentLayoutContact.View {
    private RecyclerView mRcvMenu;
    private RecyclerView mRcvListImage;
    private DrawerLayoutContract.Presenter mDrawerLayoutPresenter;
    private ContentLayoutContact.Presenter mContentLayoutPresenter;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    private ListImageAdapter mListImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mRcvMenu = findViewById(R.id.rcvTest);
        mRcvListImage = findViewById(R.id.rcvListImage);
        mDrawerLayoutPresenter = new DrawerLayoutPresenter(this, new RequestApiLocalTest());
        mContentLayoutPresenter = new ContentLayoutPresenter(this, new RequestApiLocalTest());

        mDrawerLayoutAdapter = new DrawerLayoutAdapter(this);
        mRcvMenu.setAdapter(mDrawerLayoutAdapter);
        mRcvMenu.setLayoutManager(new LinearLayoutManager(this));
        mRcvMenu.setHasFixedSize(true);
        mDrawerLayoutAdapter.setMenuOnItemClick((item, position) -> {
            mContentLayoutPresenter.loadListImage(this);
        });

        mListImageAdapter = new ListImageAdapter(this);
        mRcvListImage.setAdapter(mListImageAdapter);
        mRcvListImage.setLayoutManager(new LinearLayoutManager(this));
        mRcvListImage.setHasFixedSize(true);
        mListImageAdapter.setListImageOnclick((item, position) -> {
            replaceFragment(ShowImageFragment.newInstance(item, position));
        });
        mDrawerLayoutPresenter.loadMenu(this);
    }

    @Override
    public void onLoadMenu(ArrayList<Menu> mMenus) {
        mDrawerLayoutAdapter.addMenus(mMenus);
    }

    @Override
    public void onBeforeLoadMenu() {

    }

    @Override
    public void onLoadListImage(ArrayList<DataImage> dataImages) {
        mListImageAdapter.addDataImages(dataImages);
    }

    @Override
    public void onBeforeLoadListImage() {

    }

    @Override
    public void onError(ApiException throwable) {

    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
}
