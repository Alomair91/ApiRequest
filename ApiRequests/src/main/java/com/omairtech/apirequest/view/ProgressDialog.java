package com.omairtech.apirequest.view;

import android.app.Activity;
import android.content.Context;

public class ProgressDialog {

    private static volatile ProgressDialog instance;

    public static ProgressDialog getInstance() {
        if (instance == null)
            instance = new ProgressDialog();
        return instance;
    }

    private android.app.ProgressDialog progressDialog;

    public void show(Context context, int message) {
        show(context, context.getString(message));
    }

    public void show(Context context, String message) {
        if (isActivityFinished(context)) {
            return;
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        //if (progressDialog == null) {
            progressDialog = new android.app.ProgressDialog(context);
            progressDialog.setCancelable(false);
        //}

        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void hide() {
        if (progressDialog == null) {
            return;
        }
        progressDialog.dismiss();
    }

    protected boolean isActivityFinished(Context context) {
        if (context instanceof Activity) {
            return ((Activity) context).isFinishing();
        }
        return true;
    }
}
