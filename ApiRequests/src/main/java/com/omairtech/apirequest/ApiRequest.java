package com.omairtech.apirequest;

import android.app.Activity;
import android.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.omairtech.apirequest.Interface.ApiRequestInterface;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.volley.VolleyJSONRequest;
import com.omairtech.apirequest.volley.VolleyStringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiRequest extends BaseHelper {

    public ApiRequest(Activity activity) {
        this.activity = activity;
    }

    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url) {

        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
    }

    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      HashMap<String, String> header) {

        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.header = header;
    }

    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      Map<String, String> params) {

        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.params = params;
    }

    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> params) {

        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.header = header;
        this.params = params;
    }

    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> params,
                      boolean showProgress) {
        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.header = header;
        this.params = params;
        this.showProgress = showProgress;
    }


    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> params,
                      boolean showProgress,
                      int tempId) {
        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.header = header;
        this.params = params;
        this.showProgress = showProgress;
        this.tempId = tempId;
    }


    public ApiRequest(Activity activity,
                      ApiRequestInterface apiRequestInterface,
                      int requestMethod,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> params,
                      int initialTimeoutMs) {
        this.activity = activity;
        this.apiRequestInterface = apiRequestInterface;
        this.requestMethod = requestMethod;
        this.url = url;
        this.header = header;
        this.params = params;
        this.initialTimeoutMs = initialTimeoutMs;
    }

    public void execute() {
        showLogMessage(LOG, "Request type: " + requestType);
        showLogMessage(LOG, "Request method: " + requestMethod);
        showLogMessage(LOG, "url: " + url);
        showLogMessage(LOG, "Header: " + header.toString());
        showLogMessage(LOG, "Body: " + params.toString());

        if (showProgress)
            showProgressDialog(R.string.loading);


        switch (requestMethod) {
            case POST:
                postDataToServer();
                params.put("_method", "post");
                break;
            case PUT:
                params.put("_method", "put");
                postDataToServer();
                break;
            case DELETE:
                params.put("_method", "delete");
                postDataToServer();
                break;
            case GET:
            default:
                getDataFromServer();
                break;
        }
    }


    private static RequestQueue requestQueue;

    private void getDataFromServer() {

        //Creating a Request Queue
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(activity);

        if (requestType == REQUEST_TYPE_STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.GET, url
                    , this::getStringResponse, this::getError, initialTimeoutMs, tag);
            //Adding request to the queue
            requestQueue.add(stringRequest);

        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(Request.Method.GET, url
                    , this::getJSONResponse, this::getError, initialTimeoutMs, tag);
            //Adding request to the queue
            requestQueue.add(jsonRequest);
        }
    }

    private void postDataToServer() {
        //Creating a Request Queue
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(activity);
        if (requestType == REQUEST_TYPE_STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url
                    , this::getStringResponse, this::getError, initialTimeoutMs, tag, params);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(Request.Method.POST, url
                    , this::getJSONResponse, this::getError, initialTimeoutMs, tag, params);
            jsonRequest.setTag(tag);
            //Adding request to the queue
            requestQueue.add(jsonRequest);
        }
    }

    private void getStringResponse(String response) {
        showLogMessage(LOG, response);
        if (showProgress) hideProgressDialog();
        if (apiRequestInterface != null) {
            if (tempId != 0)
                apiRequestInterface.onServerResponse(response, tempId);
            else
                apiRequestInterface.onServerResponse(response);
        }
    }

    private void getJSONResponse(JSONObject jsonObject) {
        showLogMessage(LOG, jsonObject.toString());
        if (showProgress) hideProgressDialog();

        if (apiRequestInterface != null) {
            if (tempId != 0)
                apiRequestInterface.onServerResponse(jsonObject, tempId);
            else
                apiRequestInterface.onServerResponse(jsonObject);
        }
    }

    private void getError(VolleyError volleyError) {
        showLogMessage(LOG, volleyError.toString());
        if (showProgress) hideProgressDialog();

        if (apiRequestInterface != null) {
            if (tempId != 0)
                apiRequestInterface.onServerError(volleyError.getMessage(), tempId);
            else
                apiRequestInterface.onServerError(volleyError.getMessage());
        }

        if (resendAgain && !activity.isFinishing()) {
            new AlertDialog.Builder(activity)
                    .setMessage(activity.getString(R.string.connection_error_please_try_again))
                    .setCancelable(false)
                    .setPositiveButton(R.string.connect, (dialog, which) -> execute())
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}
