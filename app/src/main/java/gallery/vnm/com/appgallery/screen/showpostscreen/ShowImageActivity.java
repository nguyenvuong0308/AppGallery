package gallery.vnm.com.appgallery.screen.showpostscreen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Objects;

import gallery.vnm.com.appgallery.MyApplication;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.customview.DialogCustom;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.DataImageTmp;
import gallery.vnm.com.appgallery.model.TypePost;
import gallery.vnm.com.appgallery.screen.editscreen.EditActivity;
import gallery.vnm.com.appgallery.screen.showpostscreen.adapter.ImageCommentAdapter;
import gallery.vnm.com.appgallery.utils.DownloadControl;
import gallery.vnm.com.appgallery.utils.YoutubeConfig;

import static gallery.vnm.com.appgallery.screen.mainscreen.MainActivity.REQUEST_EDIT_MESSAGE;

public class ShowImageActivity extends YouTubeBaseActivity {
    private static final String TAG = "ShowImageActivity";
    private ImageCommentAdapter mImageCommentAdapter;
    private DataImageTmp mDataImageTmp;
    private TextView mTvMessage;
    private ImageView mIvActionMore;
    private TextView mTvWriterName;
    private TextView mTvAlbumName;
    private ImageView mIvWriterThumb;
    private ImageView mIvVideoThumb;
    private ImageView mIvDownload;
    private ImageView mIvPlay;
    private ImageView mIvBack;
    private RecyclerView mRcvImage;
    private MyApplication mMyApplication;
    private int mPositionSelected;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;
    private boolean mResultOk = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_preview_image);
        mTvMessage = findViewById(R.id.tvMessage);
        mIvActionMore = findViewById(R.id.ivActionMore);
        mIvActionMore = findViewById(R.id.ivActionMore);
        mTvWriterName = findViewById(R.id.tvWriterName);
        mTvAlbumName = findViewById(R.id.tvAlbumName);
        mIvWriterThumb = findViewById(R.id.ivWriterThumb);
        mIvVideoThumb = findViewById(R.id.ivVideoThumb);
        mIvBack = findViewById(R.id.ivBack);
        mIvDownload = findViewById(R.id.ivDownload);
        mIvPlay = findViewById(R.id.ivPlay);
        mMyApplication = (MyApplication) getApplication();
        mRcvImage = findViewById(R.id.rcvImages);
        youTubePlayerView = findViewById(R.id.youtubeView);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(mDataImageTmp.getDataImage().getVideoInfo().getIdVideoYoutube());
                mIvVideoThumb.setVisibility(View.GONE);
                Log.d(TAG, "Success");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "failure");
            }
        };
        initData();
    }

    private void initData() {
        mDataImageTmp = mMyApplication.getDataImageTmp();
        mPositionSelected = mMyApplication.getPosition();
        mTvWriterName.setText(TextUtils.isEmpty(mDataImageTmp.getDataImage().getWriterName()) ? "Unknown" : mDataImageTmp.getDataImage().getWriterName());
        mTvAlbumName.setText(TextUtils.isEmpty(mDataImageTmp.getAlbum().getAlbumName()) ? "Unknown" : mDataImageTmp.getAlbum().getAlbumName());
        mTvMessage.setText(mDataImageTmp.getDataImage().getMessage());
        if (mDataImageTmp.getDataImage().getPostType().equals(TypePost.VIDEO)) {
            mIvDownload.setVisibility(View.VISIBLE);
            mRcvImage.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.VISIBLE);
            mIvPlay.setVisibility(View.VISIBLE);
            mIvVideoThumb.setVisibility(View.VISIBLE);
            Glide.with(this).load(mDataImageTmp.getDataImage().getVideoInfo().getUrlThumb()).into(mIvVideoThumb);
        } else {
            mIvVideoThumb.setVisibility(View.GONE);
            mIvDownload.setVisibility(View.GONE);
            youTubePlayerView.setVisibility(View.GONE);
            mIvPlay.setVisibility(View.GONE);
            mRcvImage.setVisibility(View.VISIBLE);

            mImageCommentAdapter = new ImageCommentAdapter(this, mDataImageTmp.getDataImage().getImages());
            mRcvImage.setHasFixedSize(false);
            mRcvImage.setLayoutManager(new LinearLayoutManager(this));
            mRcvImage.setAdapter(mImageCommentAdapter);
            mImageCommentAdapter.setOnItemClick((item, position) -> {
                ArrayList<DataImage.Image> image = new ArrayList<>();
                image.add(item);
                Toast.makeText(this, "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
                DownloadControl.downloadFiles(this, image, mDataImageTmp.getAlbum().getAlbumName() + "_" + mDataImageTmp.getDataImage().getTextClientId(), new DownloadControl.OnDownloadCallBack() {
                    @Override
                    public void onLimitDownload() {
                        Toast.makeText(getApplicationContext(), "Bạn đã hết số lượt tải trong ngày!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadError() {
                        Toast.makeText(getApplicationContext(), "Không thể tải về!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        }

        Glide.with(this).load(mDataImageTmp.getDataImage().getWriterThumb()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mIvWriterThumb.setImageResource(R.drawable.ic_noimage);
                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(mIvWriterThumb);
        mIvActionMore.setOnClickListener(this::showPopupMenu);
        mIvBack.setOnClickListener(view -> {
            if (mResultOk) {
                setResult(RESULT_OK);
                finish();
            } else {
                finish();
            }
        });

        mIvPlay.setOnClickListener(view -> {
            youTubePlayerView.initialize(YoutubeConfig.API_KEY, mOnInitializedListener);
            mIvPlay.setVisibility(View.GONE);
        });
        mIvDownload.setOnClickListener(view -> {
            Toast.makeText(this, "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
            ArrayList<String> urls = new ArrayList<>();
            urls.add(mDataImageTmp.getDataImage().getVideoInfo().getUrlDownload());
            urls.add(mDataImageTmp.getDataImage().getVideoInfo().getUrlThumb());
              DownloadControl.downloadFileByUrls(this, urls, mDataImageTmp.getAlbum().getAlbumName() + "_" + mDataImageTmp.getDataImage().getTextClientId(), new DownloadControl.OnDownloadCallBack() {
                @Override
                public void onLimitDownload() {
                    Toast.makeText(getApplicationContext(), "Bạn đã hết số lượt tải trong ngày!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDownloadError() {
                    Toast.makeText(getApplicationContext(), "Không thể tải về!", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_more_2, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item2 -> {
            switch (item2.getItemId()) {
                case R.id.edit: {
                    mMyApplication.setDataImageTmp(mDataImageTmp);
                    mMyApplication.setPosition(mPositionSelected);
                    mMyApplication.setAlbumName(mDataImageTmp.getAlbum().getAlbumName());
                    startActivityForResult(new Intent(this, EditActivity.class), REQUEST_EDIT_MESSAGE);
                }
                break;
                case R.id.copy: {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(mDataImageTmp.getDataImage().getMessage(), mDataImageTmp.getDataImage().getMessage());
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "Sap chép thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.download: {
                    Toast.makeText(this, "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
                    DownloadControl.downloadFiles(Objects.requireNonNull(this), mDataImageTmp.getDataImage().getImages(), mDataImageTmp.getAlbum().getAlbumName() + "_" + mDataImageTmp.getDataImage().getTextClientId(), new DownloadControl.OnDownloadCallBack() {
                        @Override
                        public void onLimitDownload() {
                            Toast.makeText(getApplicationContext(), "Bạn đã hết số lượt tải trong ngày!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDownloadError() {
                            Toast.makeText(getApplicationContext(), "Không thể tải về!", Toast.LENGTH_SHORT).show();
                        }
                });
                }
                break;

                case R.id.hint: {
                    DialogCustom custom = new DialogCustom(this);
                    custom.setTitle("Hint!");
                    custom.setMessage(mDataImageTmp.getDataImage().getHint());
                    custom.setPositiveAction("OK");
                    custom.show();
                }
                break;

                case R.id.tag: {
                    DialogCustom custom = new DialogCustom(this);
                    custom.setTitle("Tag!");
                    custom.setMessage(mDataImageTmp.getDataImage().getTag());
                    custom.setPositiveAction("OK");
                    custom.show();
                }
                break;
            }
            return true;
        });
        popupMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_EDIT_MESSAGE: {
                if (resultCode == RESULT_OK) {
                    mResultOk = true;
                    mTvMessage.setText(mMyApplication.getMessageChange());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mResultOk) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}

