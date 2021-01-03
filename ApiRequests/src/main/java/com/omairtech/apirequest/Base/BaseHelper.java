package com.omairtech.apirequest.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.apirequest.enums.InitialTimeout;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;

import java.util.Hashtable;
import java.util.Map;

public class BaseHelper {
    public static final String LOG = ApiRequest.class.getSimpleName();

    private Activity activity;
    private ApiRequestListener listener;
    private RequestType requestType;

    private String url;
    private Map<String, String> headerParams = new Hashtable<>();
    private Map<String, String> bodyParams = new Hashtable<>();

    private int tempId = 0;
    private String tag;

    private ResponseType responseType = ResponseType.JSON;
    private int initialTimeoutMs = InitialTimeout.Time50Second;

    private boolean showProgress = true;
    private boolean showTryRequestAgain = true;
    private boolean showLog = false;

    private boolean setPUTAndDELETEAsPOST = false;


    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public Activity getActivity() {
        return activity;
    }

    /**
     * Callback interface to get response or error in your activity
     * @param listener interface
     */
    public void setListener(ApiRequestListener listener) {
        this.listener = listener;
    }
    public ApiRequestListener getListener() {
        return listener;
    }

    /**
     * HTTP Request type
     * @param requestType One of the following: [0=GET, 1=POST, 2=PUT, 3=DELETE]
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    public RequestType getRequestType() {
        return requestType;
    }



    /**
     *
     * @param url String
     */
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    /**
     * Header Params
     * @param params HashMap
     */
    public void setHeaderParams(Map<String, String> params) {
        this.headerParams = params;
    }
    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    /**
     *  Body Params
     * @param params HashMap
     */
    public void setBodyParams(Map<String, String> params) {
        this.bodyParams = params;
    }
    public Map<String, String> getBodyParams() {
        return bodyParams ;
    }



    /**
     *
     * @param tempId int
     */
    public void setTempId(int tempId) {
        this.tempId = tempId;
    }
    public int getTempId() {
        return tempId;
    }

    /**
     *
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }



    /**
     * Type of request
     *
     * @param responseType type of response
     * @apiNote REQUEST_TYPE_JSON is the default
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
    public ResponseType getResponseType() {
        return responseType;
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
    public int getInitialTimeoutMs() {
        return initialTimeoutMs;
    }



    /**
     * Show dialog until request is done
     * @param showProgress boolean
     */
    public void setShowProgressDialog(boolean showProgress) {
        this.showProgress = showProgress;
    }
    public boolean isShowProgress() {
        return showProgress;
    }

    /**
     * Show dialog to ask user if he want to recall request again if request failed
     * @param tryRequestAgain boolean
     */
    public void setShowTryRequestAgain(boolean tryRequestAgain) {
        this.showTryRequestAgain = tryRequestAgain;
    }
    public boolean isShowTryRequestAgain() {
        return showTryRequestAgain;
    }

    /**
     * show log message in logcat
     * @param showLog boolean
     */
    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }
    public boolean isShowLog() {
        return showLog;
    }

    /**
     * if you want to send put and delete request as post request and pass request type in body as new param
     * like ["_method":"put"] or ["_method":"delete"]
     * @param setPUTAndDELETEAsPOST boolean default false
     */
    public void setSetPUTAndDELETEAsPOST(boolean setPUTAndDELETEAsPOST) {
        this.setPUTAndDELETEAsPOST = setPUTAndDELETEAsPOST;
    }
    public boolean isSetPUTAndDELETEAsPOST() {
        return setPUTAndDELETEAsPOST;
    }

    // Utils ==========================================================================
    /**
     * show log message if use active it
     * @param message String
     */
    protected void showLogMessage(String message) {
        Log.e(BaseHelper.LOG, message +" ");
    }

    private ProgressDialog progressDialog;
    protected void showProgressDialog(int messageId) {
        if (!isShowProgress()) {
            // User doesn't need to show progress dialog
            return;
        }

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
    protected void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }
        progressDialog.dismiss();
    }
}
