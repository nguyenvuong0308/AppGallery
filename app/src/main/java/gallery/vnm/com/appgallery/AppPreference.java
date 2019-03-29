package gallery.vnm.com.appgallery;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String PREF_NAME = "AppGallery";
    public static void setAccountName(String accountName, Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        if (accountName == null || accountName.isEmpty()) {
            editor.remove(PREF_ACCOUNT_NAME);
        } else {
            editor.putString(PREF_ACCOUNT_NAME, accountName);
        }
        editor.apply();
    }

    public static String getAccountName(Context context) {
        SharedPreferences preferences = getPreferences(context);
        return preferences.getString(PREF_ACCOUNT_NAME, null);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
