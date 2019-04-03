package gallery.vnm.com.appgallery.Screen.editscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import gallery.vnm.com.appgallery.ImageAdapter;
import gallery.vnm.com.appgallery.MyApplication;
import gallery.vnm.com.appgallery.R;
import gallery.vnm.com.appgallery.SpanSizeLookup1x1;
import gallery.vnm.com.appgallery.SpanSizeLookup1x2;
import gallery.vnm.com.appgallery.SpanSizeLookup1x3;
import gallery.vnm.com.appgallery.SpanSizeLookup2x1;
import gallery.vnm.com.appgallery.SpanSizeLookup2x2;
import gallery.vnm.com.appgallery.model.DataImage;

public class EditActivity extends AppCompatActivity implements EditScreenContract.View {
    private RecyclerView mRcvImages;
    private EditText mEdtMessage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        initView();
    }

    private void initView() {
        mRcvImages = findViewById(R.id.rcvImages);
        mEdtMessage = findViewById(R.id.edtMessage);
        MyApplication application = (MyApplication) getApplication();
        GridLayoutManager layoutManager;
        DataImage dataImage = application.getDataImageTmp();
        mEdtMessage.setText(dataImage.getMessage());
        ImageAdapter imageAdapter = new ImageAdapter(this, application.getDataImageTmp().getImages());
        switch (dataImage.getPostType()) {
            case "vuong2x1":
                layoutManager = new GridLayoutManager(this, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x1());
                break;
            case "vuong1+3":
                layoutManager = new GridLayoutManager(this, 3);
                imageAdapter.setMaxSize(4);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
                break;
            case "vuong1+2":
                layoutManager = new GridLayoutManager(this, 2);
                imageAdapter.setMaxSize(3);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup1x2());
                break;
            case "vuong2x2":
                imageAdapter.setMaxSize(4);
                layoutManager = new GridLayoutManager(this, 2);
                layoutManager.setSpanSizeLookup(new SpanSizeLookup2x2());
                break;
            case "vuongFull":
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
    }
}
