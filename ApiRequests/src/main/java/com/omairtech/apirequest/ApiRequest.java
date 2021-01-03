package com.omairtech.apirequest;

import android.app.Activity;
import android.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.volley.VolleyJSONRequest;
import com.omairtech.apirequest.volley.VolleyStringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiRequest extends BaseHelper {

    public ApiRequest(Activity activity) {
        setActivity(activity);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener) {
        setActivity(activity);
        setListener(listener);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType) {
        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url) {
        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      HashMap<String, String> header) {

        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      Map<String, String> body) {

        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setBodyParams(body);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> body) {

        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
    }

    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> body,
                      boolean showProgress) {
        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
        setShowProgressDialog(showProgress);
    }


    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> body,
                      boolean showProgress,
                      int tempId) {
        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
        setShowProgressDialog(showProgress);
        setTempId(tempId);
    }


    public ApiRequest(Activity activity,
                      ApiRequestListener listener,
                      RequestType requestType,
                      String url,
                      HashMap<String, String> header,
                      Map<String, String> body,
                      int initialTimeoutMs) {
        setActivity(activity);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
        setInitialTimeoutMs(initialTimeoutMs);
    }

    public void execute() {
        if (isShowLog()) {
            showLogMessage("Request type: " + getRequestType());
            showLogMessage("Response type: " + getResponseType());
            showLogMessage("URL: " + getUrl());
            showLogMessage("Header params: " + getHeaderParams().toString());
            showLogMessage("Body params: " + getBodyParams().toString());
        }

        showProgressDialog(R.string.loading);
        switch (getRequestType()) {
            case POST:
            case PUT:
            case DELETE:
                if (isSetPUTAndDELETEAsPOST()) {
                    Map<String, String> bodyParams = getBodyParams();
                    if (getRequestType() == RequestType.POST)
                        bodyParams.put("_method", "post");
                    else if (getRequestType() == RequestType.PUT)
                        bodyParams.put("_method", "put");
                    else if (getRequestType() == RequestType.DELETE)
                        bodyParams.put("_method", "delete");
                    setBodyParams(bodyParams);
                }
                postDataToServer();
                break;
            case GET:
            default:
                getDataFromServer();
                break;
        }
    }


    private static RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(getActivity());
        return requestQueue;
    }

    private void getDataFromServer() {
        if (getResponseType() == ResponseType.STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.GET, getUrl()
                    , this::getStringResponse, this::getError, getInitialTimeoutMs(), getTag(), getHeaderParams());
            //Adding request to the queue
            getRequestQueue().add(stringRequest);

        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(Request.Method.GET, getUrl()
                    , this::getJSONResponse, this::getError, getInitialTimeoutMs(), getTag(), getHeaderParams());
            //Adding request to the queue
            getRequestQueue().add(jsonRequest);
        }
    }

    private void postDataToServer() {
        int requestMethod = Request.Method.POST;
        if (isSetPUTAndDELETEAsPOST()) {
            if (getRequestType() == RequestType.PUT)
                requestMethod = Request.Method.PUT;
            else if (getRequestType() == RequestType.DELETE)
                requestMethod = Request.Method.DELETE;
        }

        if (getResponseType() == ResponseType.STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(requestMethod, getUrl()
                    , this::getStringResponse, this::getError, getInitialTimeoutMs(), getTag(), getHeaderParams(), getBodyParams());
            //Adding request to the queue
            getRequestQueue().add(stringRequest);
        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(requestMethod, getUrl()
                    , this::getJSONResponse, this::getError, getInitialTimeoutMs(), getTag(), getHeaderParams(), getBodyParams());
            //Adding request to the queue
            getRequestQueue().add(jsonRequest);
        }
    }

    private void getStringResponse(String response) {
        if (isShowLog())
            showLogMessage(response);

        if (isShowProgress())
            hideProgressDialog();

        if (getListener() != null) {
            getListener().onApiRequestResponse(response);
            if (getTempId() != 0)
                getListener().onApiRequestResponse(response, getTempId());
        }
    }

    private void getJSONResponse(JSONObject jsonObject) {
        if (isShowLog())
            showLogMessage(jsonObject.toString());

        if (isShowProgress())
            hideProgressDialog();

        if (getListener() != null) {
            getListener().onApiRequestResponse(jsonObject);
            getListener().onApiRequestResponse(jsonObject.toString());
            if (getTempId() != 0) {
                getListener().onApiRequestResponse(jsonObject, getTempId());
                getListener().onApiRequestResponse(jsonObject.toString(), getTempId());
            }
        }
    }

    private void getError(VolleyError volleyError) {
        if (isShowLog())
            showLogMessage(volleyError.getMessage());

        if (isShowProgress())
            hideProgressDialog();

        if (getListener() != null) {
            getListener().onApiRequestError(volleyError.toString());
            if (getTempId() != 0)
                getListener().onApiRequestError(volleyError.toString(), getTempId());
        }

        if (isShowTryRequestAgain() && !getActivity().isFinishing()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(getActivity().getString(R.string.connection_error_please_try_again))
                    .setCancelable(false)
                    .setPositiveButton(R.string.connect, (dialog, which) -> execute())
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}
