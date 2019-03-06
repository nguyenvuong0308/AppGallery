package gallery.vnm.com.appgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by nguye on 3/6/2019.
 */

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRcv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcv = findViewById(R.id.rcvTest);
        mRcv.setAdapter(new ImageAdapter(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(new SpanSizeLookup1x3());
        mRcv.setLayoutManager(layoutManager);
        mRcv.setHasFixedSize(true);
    }
}
