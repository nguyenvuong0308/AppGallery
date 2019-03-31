package gallery.vnm.com.appgallery.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.ArrayList;
import java.util.Arrays;

import gallery.vnm.com.appgallery.AppPreference;
import gallery.vnm.com.appgallery.ListImageAdapter;
import gallery.vnm.com.appgallery.LoginActivity;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.Screen.drawerlayout.ContentLayoutContact;
import gallery.vnm.com.appgallery.Screen.drawerlayout.ContentLayoutPresenter;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutAdapter;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutContract;
import gallery.vnm.com.appgallery.Screen.drawerlayout.DrawerLayoutPresenter;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.Menu;
import gallery.vnm.com.appgallery.model.network.RequestApiNetwork;

/**
 * Created by nguye on 3/6/2019.
 */

public class MainActivity extends AppCompatActivity implements DrawerLayoutContract.View, ContentLayoutContact.View {
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS_READONLY};
    private static final int REQUEST_AUTHORIZATION = 12345;
    private RecyclerView mRcvMenu;
    private RecyclerView mRcvListImage;
    private DrawerLayoutContract.Presenter mDrawerLayoutPresenter;
    private ContentLayoutContact.Presenter mContentLayoutPresenter;
    private DrawerLayoutAdapter mDrawerLayoutAdapter;
    private ListImageAdapter mListImageAdapter;
    private ImageView mIvMenu;
    private TextView mTvWarning;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private GoogleAccountCredential mCredential;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        String accountName = AppPreference.getAccountName(this);
        if (accountName == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            mCredential.setSelectedAccountName(accountName);
        }
        mRcvMenu = findViewById(R.id.rcvTest);
        mSwipeRefreshLayout = findViewById(R.id.sRLayout);
        mIvMenu = findViewById(R.id.ivMenu);
        mTvWarning = findViewById(R.id.tvWarning);
        mRcvListImage = findViewById(R.id.rcvListImage);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerLayoutPresenter = new DrawerLayoutPresenter(this, new RequestApiNetwork(mCredential));
        mContentLayoutPresenter = new ContentLayoutPresenter(this, new RequestApiNetwork(mCredential));
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(this);
        mRcvMenu.setAdapter(mDrawerLayoutAdapter);
        mRcvMenu.setLayoutManager(new LinearLayoutManager(this));
        mRcvMenu.setHasFixedSize(true);
        mDrawerLayoutAdapter.setMenuOnItemClick((item, position) -> {
            mContentLayoutPresenter.refresh(this, item.getName());
            mDrawerLayout.closeDrawer(Gravity.START);
        });

        mRcvListImage.setLayoutManager(new LinearLayoutManager(this));
        mRcvListImage.setHasFixedSize(true);
        mListImageAdapter = new ListImageAdapter(this, mRcvListImage);
        mRcvListImage.setAdapter(mListImageAdapter);
        mListImageAdapter.setListImageOnclick((item, position) -> {
            replaceFragment(ShowImageFragment.newInstance(item, position));
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        });
        mDrawerLayoutPresenter.loadMenu(this);
        mIvMenu.setOnClickListener(v -> {
            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
            } else {
                mDrawerLayout.openDrawer(Gravity.START);
            }

        });
        mListImageAdapter.setLoadMore(() -> {
            mContentLayoutPresenter.loadMore(this);
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mDrawerLayoutAdapter.getMenuSelected() != null) {
                mContentLayoutPresenter.refresh(this, mDrawerLayoutAdapter.getMenuSelected().getName());
            } else {
                Toast.makeText(this, "Chưa chọn album!", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mIvMenu.setImageResource(R.drawable.ic_arrow);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                if (newState == DrawerLayout.STATE_SETTLING) {
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
                    mIvMenu.startAnimation(animation);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mIvMenu.setImageResource(R.drawable.ic_menu);
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onLoadMenu(ArrayList<Menu> mMenus) {
        mDrawerLayoutAdapter.addMenus(mMenus);
    }

    @Override
    public void onBeforeLoadMenu() {
        //nothing
    }

    @Override
    public void onLoadListImage(ArrayList<DataImage> dataImages) {
        mListImageAdapter.refreshData(dataImages);
        mSwipeRefreshLayout.setRefreshing(false);
        if (dataImages == null) {
            mTvWarning.setText("Không có dữ liệu");
        } else {
            mTvWarning.setText("");
        }
    }

    @Override
    public void onLoadMore(ArrayList<DataImage> dataImages) {
        mListImageAdapter.addDataImages(dataImages);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onBeforeLoadListImage() {
        //nothing
    }

    @Override
    public void onError(Exception throwable) {
        if (throwable instanceof UserRecoverableAuthIOException) {
            startActivityForResult(
                    ((UserRecoverableAuthIOException) throwable).getIntent(),
                    REQUEST_AUTHORIZATION);
            return;
        }

        mTvWarning.setText("Có lỗi xảy ra!\n" + throwable.getMessage());
        mListImageAdapter.refreshData(null);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_AUTHORIZATION: {
                if (resultCode == RESULT_OK) {
                    mContentLayoutPresenter.tryReload(this);
                    mDrawerLayoutPresenter.tryReload(this);
                }
            }
            break;
        }
    }
}
