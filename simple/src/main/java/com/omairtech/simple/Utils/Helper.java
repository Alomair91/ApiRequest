package com.omairtech.simple.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.omairtech.simple.BuildConfig;
import com.omairtech.apirequest.R;

public class Helper {

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null) {
            Toast.makeText(context, context.getString(R.string.please_check_your_connection_and_try_again), Toast.LENGTH_SHORT).show();
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

    public static void showLogMessage(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.e(tag, message);
    }
}
