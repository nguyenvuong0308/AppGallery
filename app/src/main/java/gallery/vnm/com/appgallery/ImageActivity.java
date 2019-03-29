package gallery.vnm.com.appgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;

public class ImageActivity extends AppCompatActivity {
    private GoogleAccountCredential mCredential;
    private static final String[] SCOPES = { SheetsScopes.SPREADSHEETS_READONLY };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize credentials and service object.
//        mCredential = GoogleAccountCredential.usingOAuth2(
//                getApplicationContext(), Arrays.asList(SCOPES))
//                .setBackOff(new ExponentialBackOff());
//        String accountName = AppPreference.getAccountName(this);
//        if ( accountName == null) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        } else {
//            mCredential.setSelectedAccountName(accountName);
//        }
    }
}
