package gallery.vnm.com.appgallery.Screen.editscreen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import gallery.vnm.com.appgallery.ImageAdapter;
import gallery.vnm.com.appgallery.MyApplication;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.SpanSizeLookup1x1;
import gallery.vnm.com.appgallery.SpanSizeLookup1x2;
import gallery.vnm.com.appgallery.SpanSizeLookup1x3;
import gallery.vnm.com.appgallery.SpanSizeLookup2x1;
import gallery.vnm.com.appgallery.SpanSizeLookup2x2;
import gallery.vnm.com.appgallery.TypePost;
import gallery.vnm.com.appgallery.model.DataImage;
import gallery.vnm.com.appgallery.model.local.MessageComment;
import io.realm.Realm;

public class EditActivity extends AppCompatActivity implements EditScreenContract.View {
    private RecyclerView mRcvImages;
    private EditText mEdtMessage;
    private ImageView mIvBack;
    private ImageView mIvActionMore;
    private ImageView mIvWriterThumb;
    private TextView mTvSave;
    private TextView mTvWriterName;
    private TextView mTvAlbumName;
    private DataImage mDataImage;
    private MyApplication mMyApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        initView();
    }

    private void initView() {
        mMyApplication = (MyApplication) getApplication();
        mIvBack = findViewById(R.id.ivBack);
        mTvSave = findViewById(R.id.tvSave);
        mIvActionMore = findViewById(R.id.ivActionMore);
        mTvWriterName = findViewById(R.id.tvWriterName);
        mTvAlbumName = findViewById(R.id.tvAlbumName);
        mRcvImages = findViewById(R.id.rcvImages);
        mEdtMessage = findViewById(R.id.edtMessage);
        mIvWriterThumb = findViewById(R.id.ivWriterThumb);
        GridLayoutManager layoutManager;
        mDataImage = mMyApplication.getDataImageTmp();
        Glide.with(this).load(mDataImage.getWriterThumb()).listener(new RequestListener<Drawable>() {
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
        mTvWriterName.setText(TextUtils.isEmpty(mDataImage.getWriterName()) ? "Unknown" : mDataImage.getWriterName());
        mTvAlbumName.setText(TextUtils.isEmpty(mMyApplication.getAlbumName()) ? "Unknown" : mMyApplication.getAlbumName());
        mEdtMessage.setText(mDataImage.getMessage());
        mEdtMessage.setSelection(mEdtMessage.getText().toString().length());
        ImageAdapter imageAdapter = new ImageAdapter(this, mDataImage.getImages(), mDataImage.getPostType());
        switch (mDataImage.getPostType()) {
            case TypePost.VUONG2X1:
                layoutManager = new GridLayoutManager(this, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x1());
                break;
            case TypePost.VUONG1X3:
                layoutManager = new GridLayoutManager(this, 3);
                imageAdapter.setMaxSize(4);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
                break;
            case TypePost.VUONG1X2:
                layoutManager = new GridLayoutManager(this, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x2());
                break;
            case TypePost.VUONG2X2:
                imageAdapter.setMaxSize(4);
                layoutManager = new GridLayoutManager(this, 2);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x2());
                break;
            case TypePost.VUONGFULL:
                imageAdapter.setMaxSize(1);
                layoutManager = new GridLayoutManager(this, 1);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                break;
            case TypePost.POST_TYPE_1_AUTO:
                imageAdapter.setMaxSize(1);
                layoutManager = new GridLayoutManager(this, 1);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x1());
                break;
            default:
                imageAdapter.setMaxSize(2);
                layoutManager = new GridLayoutManager(this, 2);
                break;
        }
        mRcvImages.setAdapter(imageAdapter);
        mRcvImages.setLayoutManager(layoutManager);
        mRcvImages.setHasFixedSize(true);

        binEvent();
    }

    private void binEvent() {
        mIvBack.setOnClickListener(v -> {
            finish();
            hideKeyboard();
        });
        mTvSave.setOnClickListener(v -> {
            Realm realm = Realm.getDefaultInstance();
            MessageComment messageComment = realm.where(MessageComment.class).equalTo("textClientId", mDataImage.getTextClientId()).findFirst();
            if (messageComment != null) {
                realm.executeTransaction(realm1 -> {
                    messageComment.setMessageComment(mEdtMessage.getText().toString());
                });
            } else {
                realm.executeTransaction(realm1 -> {
                    Number currentIdNum = realm.where(MessageComment.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    MessageComment newMessageComment = new MessageComment();
                    newMessageComment.setTextClientId(mDataImage.getTextClientId());
                    newMessageComment.setId(nextId);
                    newMessageComment.setMessageComment(mEdtMessage.getText().toString());
                    realm.insertOrUpdate(newMessageComment);
                });
            }
            mMyApplication.setMessageChange(mEdtMessage.getText().toString());
            setResult(RESULT_OK);
            hideKeyboard();
            finish();
        });

        mIvActionMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, mIvActionMore);
            popupMenu.getMenuInflater().inflate(R.menu.popup_more, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.copy: {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(mEdtMessage.getText(), mEdtMessage.getText());
                        if (clipboard != null) {
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(this, "Sap chép thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                    case R.id.download: {
                        DownloadControl.downloadFiles(this, mDataImage.getImages(), mMyApplication.getAlbumName() + "_" + mDataImage.getTextClientId());
                        Toast.makeText(this, "Đang tải ảnh về...", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.hint: {
                        DialogCustom custom = new DialogCustom(this);
                        custom.setTitle("Hint!");
                        custom.setMessage(mDataImage.getHint());
                        custom.setPositiveAction("OK");
                        custom.show();
                    }
                    break;

                    case R.id.tag: {
                        DialogCustom custom = new DialogCustom(this);
                        custom.setTitle("Tag!");
                        custom.setMessage(mDataImage.getTag());
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

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
