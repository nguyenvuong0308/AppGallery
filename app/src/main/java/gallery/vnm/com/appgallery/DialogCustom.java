package gallery.vnm.com.appgallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogCustom {
    AlertDialog alertDialog;
    AlertDialog.Builder mBuilder;
    private Context mContext;

    public DialogCustom(Context context) {
        mContext = context;
        mBuilder = new AlertDialog.Builder(mContext);
    }

    public void setTitle(String title) {
        mBuilder.setTitle(title);
    }

    public void setMessage(String message) {
        mBuilder.setMessage(message);
    }

    public void setPositiveAction(String textButton, DialogInterface.OnClickListener positiveAction) {
        mBuilder.setPositiveButton(textButton, positiveAction)
                .setCancelable(false);
    }

    public void setPositiveAction(String textButton) {
        mBuilder.setPositiveButton(textButton, (dialogInterface, i) -> {
        })
                .setCancelable(false);
    }

    public void setNegativeAction(String textButton, DialogInterface.OnClickListener negativeAction) {
        mBuilder.setNegativeButton(textButton, negativeAction)
                .setCancelable(false);
    }

    public void show() {
        alertDialog = mBuilder.create();
        alertDialog.show();
    }
}
