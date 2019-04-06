package gallery.vnm.com.appgallery.Screen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import gallery.vnm.com.appgallery.DialogCustom;
import gallery.vnm.com.appgallery.DownloadControl;
import gallery.vnm.com.appgallery.ImagePagerAdapter;
import gallery.vnm.com.appgallery.MyApplication;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.Screen.editscreen.EditActivity;
import gallery.vnm.com.appgallery.model.DataImageTmp;

import static android.app.Activity.RESULT_OK;
import static gallery.vnm.com.appgallery.Screen.MainActivity.REQUEST_EDIT_MESSAGE;

public class ShowImageFragment extends Fragment {
    public static final String ARGS_DATA_IMAGE = "ARGS_DATA_IMAGE";
    public static final String ARGS_POSITION_SELECTED = "ARGS_POSITION_SELECTED";
    private View mRootView;
    private ViewPager mImageViewPager;
    private ImagePagerAdapter mImagePagerAdapter;
    private DataImageTmp mDataImageTmp;
    private TabLayout mTabLayout;
    private TextView mTvMessage;
    private ImageView mIvActionMore;
    private TextView mTvWriterName;
    private TextView mTvAlbumName;
    private ImageView mIvWriterThumb;
    private MyApplication mMyApplication;
    private int mPositionSelected;

    public static ShowImageFragment newInstance(DataImageTmp dataImageTmp, int positionSelected) {
        ShowImageFragment fragment = new ShowImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_DATA_IMAGE, dataImageTmp);
        args.putInt(ARGS_POSITION_SELECTED, positionSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_preview_image, container, false);
        mTvMessage = mRootView.findViewById(R.id.tvMessage);
        mIvActionMore = mRootView.findViewById(R.id.ivActionMore);
        mIvActionMore = mRootView.findViewById(R.id.ivActionMore);
        mTvWriterName = mRootView.findViewById(R.id.tvWriterName);
        mTvAlbumName = mRootView.findViewById(R.id.tvAlbumName);
        mIvWriterThumb = mRootView.findViewById(R.id.ivWriterThumb);
        mMyApplication = (MyApplication) getActivity().getApplication();
        mImageViewPager = mRootView.findViewById(R.id.vpImage);
        mTabLayout = mRootView.findViewById(R.id.tabLayout);
        if (getArguments() != null) {
            mDataImageTmp = getArguments().getParcelable(ARGS_DATA_IMAGE);
            mPositionSelected = getArguments().getInt(ARGS_POSITION_SELECTED);
            mTvWriterName.setText(TextUtils.isEmpty(mDataImageTmp.getDataImage().getWriterName()) ? "Unknown" : mDataImageTmp.getDataImage().getWriterName());
            mTvAlbumName.setText(TextUtils.isEmpty(mDataImageTmp.getAlbum().getAlbumName()) ? "Unknown" : mDataImageTmp.getAlbum().getAlbumName());
            mTvMessage.setText(mDataImageTmp.getDataImage().getMessage());
            mImagePagerAdapter = new ImagePagerAdapter(getContext(), mDataImageTmp.getDataImage().getImages());
            mImageViewPager.setAdapter(mImagePagerAdapter);
            mTabLayout.setupWithViewPager(mImageViewPager);
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
            mIvActionMore.setOnClickListener(view -> {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_more_2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item2 -> {
                    switch (item2.getItemId()) {
                        case R.id.edit: {
                            mMyApplication.setDataImageTmp(mDataImageTmp.getDataImage());
                            mMyApplication.setPosition(mPositionSelected);
                            mMyApplication.setAlbumName(mDataImageTmp.getAlbum().getAlbumName());
                            getActivity().startActivityForResult(new Intent(getContext(), EditActivity.class), REQUEST_EDIT_MESSAGE);
                        }
                        break;
                        case R.id.copy: {
                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText(mDataImageTmp.getDataImage().getMessage(), mDataImageTmp.getDataImage().getMessage());
                            if (clipboard != null) {
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getContext(), "Sap chép thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                        case R.id.download: {
                            DownloadControl.downloadFiles(getContext(), mDataImageTmp.getDataImage().getImages(), mDataImageTmp.getAlbum().getAlbumName() +"_"+mDataImageTmp.getDataImage().getTextClientId());
                            Toast.makeText(getContext(), "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
                        }
                        break;

                        case R.id.hint: {
                            DialogCustom custom = new DialogCustom(getContext());
                            custom.setTitle("Hint!");
                            custom.setMessage(mDataImageTmp.getDataImage().getHint());
                            custom.setPositiveAction("OK");
                            custom.show();
                        }
                        break;

                        case R.id.tag: {
                            DialogCustom custom = new DialogCustom(getContext());
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
            });
        }
        return mRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_EDIT_MESSAGE: {
                if (resultCode == RESULT_OK) {
                    mTvMessage.setText(mMyApplication.getMessageChange());
                }
            }
        }
    }
}
