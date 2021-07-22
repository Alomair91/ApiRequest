package com.omairtech.apirequest.view;

import android.app.Activity;
import android.content.Context;

import com.omairtech.apirequest.R;

public class AlertDialog {
    public interface Listener {
        void onClick(boolean tryAgain);
    }

    private static volatile AlertDialog instance;

    public static AlertDialog getInstance() {
        if (instance == null)
            instance = new AlertDialog();
        return instance;
    }

    private android.app.AlertDialog alertDialog;

    public void show(Context context, int message, Listener listener) {
        show(context, context.getString(message), listener);
    }

    public void show(Context context, String message, Listener listener) {
        if (isActivityFinished(context)) {
            return;
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }

        //if (alertDialog == null) {
            alertDialog = new android.app.AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setPositiveButton(R.string.connect, (dialog, which) -> listener.onClick(true))
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .create();
        //}

        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public void hide() {
        if (alertDialog == null) {
            return;
        }
        alertDialog.dismiss();
    }

    protected boolean isActivityFinished(Context context) {
        if (context instanceof Activity) {
            return ((Activity) context).isFinishing();
        }
        return true;
    }
}
