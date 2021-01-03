package com.omairtech.simple.Util;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Utils {
    private final Application application;

    private static volatile Utils instance;
    public static Utils getInstance(Application application) {
        if (instance == null) {
            instance = new Utils(application);
        }
        return instance;
    }
    private Utils(Application application){
        this.application =application;
    }

    public Context getContext() {
       return application.getApplicationContext();
    }

    // Utils method
    public boolean isInternetAvailable() {
        return isInternetAvailable(getContext());
    }

    public void showAlertDialog(String message) {
        showAlert(getContext(), message);
    }

    public void showAlertDialog(int messageId) {
        showAlertDialog(getContext().getString(messageId));
    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showToast(int messageId) {
        Toast.makeText(getContext(), messageId, Toast.LENGTH_LONG).show();
    }

    public void showLogMessage(String tag, String message) {
        Log.e(tag, message + " ");
    }


    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            Toast.makeText(context, context.getString(com.omairtech.apirequest.R.string.please_check_your_connection_and_try_again), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public static void showAlert(Context context, String message) {
        try {
            new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setCancelable(false)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
