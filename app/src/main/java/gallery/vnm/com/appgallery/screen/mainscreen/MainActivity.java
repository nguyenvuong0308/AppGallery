package gallery.vnm.com.appgallery.screen.mainscreen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.ArrayList;
import java.util.Arrays;

import gallery.vnm.com.appgallery.MyApplication;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.customview.DialogCustom;
import gallery.vnm.com.appgallery.model.Album;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.DataImageTmp;
import gallery.vnm.com.appgallery.model.network.RequestApiNetwork;
import gallery.vnm.com.appgallery.screen.LoginActivity;
import gallery.vnm.com.appgallery.screen.drawerlayout.ContentLayoutContact;
import gallery.vnm.com.appgallery.screen.drawerlayout.ContentLayoutPresenter;
import gallery.vnm.com.appgallery.screen.drawerlayout.DrawerLayoutAdapter;
import gallery.vnm.com.appgallery.screen.drawerlayout.DrawerLayoutContract;
import gallery.vnm.com.appgallery.screen.drawerlayout.DrawerLayoutPresenter;
import gallery.vnm.com.appgallery.screen.editscreen.EditActivity;
import gallery.vnm.com.appgallery.screen.mainscreen.adapter.ListImageAdapter;
import gallery.vnm.com.appgallery.screen.showpostscreen.ShowImageActivity;
import gallery.vnm.com.appgallery.utils.AppPreference;
import gallery.vnm.com.appgallery.utils.DownloadControl;

/**
 * Created by nguye on 3/6/2019.
 */

public class MainActivity extends AppCompatActivity implements DrawerLayoutContract.View, ContentLayoutContact.View {
    public static final int REQUEST_EDIT_MESSAGE = 123;
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
    private MyApplication mMyApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        mMyApplication = (MyApplication) getApplication();
    }

    private void init() {
        /*Tao credential để đăng nhâp và google*/
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
        /*Khởi tạo presenter */
        mDrawerLayoutPresenter = new DrawerLayoutPresenter(this, new RequestApiNetwork(mCredential));
        mContentLayoutPresenter = new ContentLayoutPresenter(this, new RequestApiNetwork(mCredential));

        /*Khởi tạo menu*/
        mDrawerLayoutAdapter = new DrawerLayoutAdapter(this);
        mRcvMenu.setAdapter(mDrawerLayoutAdapter);
        mRcvMenu.setLayoutManager(new LinearLayoutManager(this));
        mRcvMenu.setHasFixedSize(true);
        mDrawerLayoutAdapter.setMenuOnItemClick((item, position) -> {
            mContentLayoutPresenter.refresh(this, item);
            mDrawerLayout.closeDrawer(Gravity.START);
        });

        /*Khởi tạo list bài post*/
        mRcvListImage.setLayoutManager(new LinearLayoutManager(this));
        mRcvListImage.setHasFixedSize(true);
        mListImageAdapter = new ListImageAdapter(this, mRcvListImage);
        mRcvListImage.setAdapter(mListImageAdapter);

        /*Khởi tạo event click vào bài viết*/
        mListImageAdapter.setOnItemClick(this::onPostItemClick);
        /*Khỏi tạo event click và nút 3 chấm*/
        mListImageAdapter.setEditOnClick(this::showPopupMenu);

        mDrawerLayoutPresenter.loadAlbums(this);
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
                mContentLayoutPresenter.refresh(this, mDrawerLayoutAdapter.getMenuSelected());
            } else {
                Toast.makeText(this, "Chưa chọn album!", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /*Lắng nghe sự kiện đóng mở của drawer layout*/
        setEventListenerDrawerLayout();
    }

    private void setEventListenerDrawerLayout() {
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

    private void onPostItemClick(DataImage item, int position) {
        DataImageTmp dataImageTmp = new DataImageTmp(item, mDrawerLayoutAdapter.getMenuSelected());
        Intent intent = new Intent(this, ShowImageActivity.class);
        mMyApplication.setDataImageTmp(dataImageTmp);
        mMyApplication.setPosition(position);
        startActivityForResult(intent, REQUEST_EDIT_MESSAGE);
    }

    private void showPopupMenu(DataImage item, View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_more_2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item2 -> {
            switch (item2.getItemId()) {
                case R.id.edit: {
                    DataImageTmp dataImageTmp = new DataImageTmp(item, mDrawerLayoutAdapter.getMenuSelected());
                    mMyApplication.setDataImageTmp(dataImageTmp);
                    mMyApplication.setPosition(position);
                    mMyApplication.setAlbumName(mDrawerLayoutAdapter.getMenuSelected().getAlbumName());
                    startActivityForResult(new Intent(MainActivity.this, EditActivity.class), REQUEST_EDIT_MESSAGE);
                }
                break;
                case R.id.copy: {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(item.getMessage(), item.getMessage());
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "Sap chép thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.download: {
                    DownloadControl.downloadFiles(this, item.getImages(), mDrawerLayoutAdapter.getMenuSelected().getAlbumName() + "_" + item.getTextClientId());
                    Toast.makeText(this, "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
                }
                break;

                case R.id.hint: {
                    DialogCustom custom = new DialogCustom(this);
                    custom.setTitle("Hint!");
                    custom.setMessage(item.getHint());
                    custom.setPositiveAction("OK");
                    custom.show();
                }
                break;

                case R.id.tag: {
                    DialogCustom custom = new DialogCustom(this);
                    custom.setTitle("Tag!");
                    custom.setMessage(item.getTag());
                    custom.setPositiveAction("OK");
                    custom.show();
                }
                break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void initView() {
        mRcvMenu = findViewById(R.id.rcvTest);
        mSwipeRefreshLayout = findViewById(R.id.sRLayout);
        mIvMenu = findViewById(R.id.ivMenu);
        mTvWarning = findViewById(R.id.tvWarning);
        mRcvListImage = findViewById(R.id.rcvListImage);
        mDrawerLayout = findViewById(R.id.drawerLayout);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onLoadAlbums(ArrayList<Album> mAlbums) {
        mDrawerLayoutAdapter.addMenus(mAlbums);
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

    @Override
    public void onSelectedAlbum(Album album) {
        mContentLayoutPresenter.refresh(this, album);
        mDrawerLayoutAdapter.setAlbumSelected(album);
    }

    @Override
    public void onUpdateRequired(String extendData) {
        DialogCustom custom = new DialogCustom(this);
        custom.setTitle(getString(R.string.title_dialog_required_update));
        custom.setMessage(getString(R.string.message_required_update));
        custom.setPositiveAction(getString(R.string.update), (dialogInterface, i) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(extendData));
            startActivity(browserIntent);
            finish();
        });
        custom.setNeutralAction(getString(R.string.ignore), (dialogInterface, i) -> {
            finish();
        });
        custom.show();
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

            case REQUEST_EDIT_MESSAGE: {
                if (resultCode == RESULT_OK) {
                    mListImageAdapter.updateMessageItem(mMyApplication.getPosition(), mMyApplication.getMessageChange());
                }
            }
        }
    }

    @Override
    public void onGetAlbumSelected(Album mAlbum) {
        mListImageAdapter.setAlbum(mAlbum);
    }

}
