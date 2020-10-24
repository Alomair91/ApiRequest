package com.omairtech.apirequest.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.omairtech.apirequest.Interface.ApiRequestInterface;

import java.util.Hashtable;
import java.util.Map;

public class BaseHelper {
    public static final String LOG = "ServerHelper";

    public static final int GET = 1;
    public static final int POST = 2;
    public static final int PUT = 3;
    public static final int DELETE = 4;

    public static final int REQUEST_TYPE_STRING = 1;
    public static final int REQUEST_TYPE_JSON = 2;

    public static final int initialTimeoutMs20C = 20000;
    public static final int initialTimeoutMs30C = 30000;
    public static final int initialTimeoutMs50C = 50000;


    public Activity activity;
    public ApiRequestInterface apiRequestInterface;
    public int requestMethod;

    public String url;
    public Map<String, String> params = new Hashtable<>();
    public Map<String, String> header = new Hashtable<>();

    public int requestType = REQUEST_TYPE_STRING;
    public int initialTimeoutMs = initialTimeoutMs50C;

    public boolean showProgress = true;
    public boolean showLog = false;
    public boolean resendAgain = true;
    public int tempId = 0;
    public String tag;


    public void setApiRequestInterface(ApiRequestInterface apiRequestInterface) {
        this.apiRequestInterface = apiRequestInterface;
    }

    public void setRequestMethod(int requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBodyParams(Map<String, String> params) {
        this.params = params;
    }

    public void setHeaderParams(Map<String, String> header) {
        this.header = header;
    }

    /**
     * Type of request
     *
     * @param requestType type of request
     * @apiNote REQUEST_TYPE_STRING is the default
     */
    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    /**
     * Initial Time out
     *
     * @param initialTimeoutMs in millisecond
     * @apiNote initialTimeoutMs50C is the default
     */
    public void setInitialTimeoutMs(int initialTimeoutMs) {
        this.initialTimeoutMs = initialTimeoutMs;
    }

    public void setShowProgressDialog(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public void setShowResendAgainDialog(boolean resendAgain) {
        this.resendAgain = resendAgain;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public void showLogMessage(String tag, String message) {
        if (showLog)
            Log.e(tag, message);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public ProgressDialog progressDialog;

    public void showProgressDialog(int messageId) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(activity.getString(messageId));
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        progressDialog.dismiss();
    }
}
