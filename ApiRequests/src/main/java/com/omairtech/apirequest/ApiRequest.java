package com.omairtech.apirequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import com.android.volley.NetworkResponse;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
                    switch (getRequestType()) {
                        case POST:
                            bodyParams.put("_method", "post");
                            break;
                        case PUT:
                            bodyParams.put("_method", "put");
                            break;
                        case DELETE:
                            bodyParams.put("_method", "delete");
                            break;
                    }
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
    private NetworkResponse response;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            //Creating a Request Queue
            requestQueue = Volley.newRequestQueue(getActivity());
        return requestQueue;
    }

    private void getDataFromServer() {
        if (getResponseType() == ResponseType.STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(
                    Request.Method.GET,
                    getUrl(),
                    this::getStringResponse,
                    this::setErrorResponse,
                    getInitialTimeoutMs(),
                    getTag(),
                    getHeaderParams(),
                    response -> this.response = response);
            //Adding request to the queue
            getRequestQueue().add(stringRequest);

        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(
                    Request.Method.GET,
                    getUrl(),
                    this::setJSONResponse,
                    this::setErrorResponse,
                    getInitialTimeoutMs(),
                    getTag(),
                    getHeaderParams(),
                    response -> this.response = response);
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
            VolleyStringRequest stringRequest = new VolleyStringRequest(
                    requestMethod,
                    getUrl(),
                    this::getStringResponse,
                    this::setErrorResponse,
                    getInitialTimeoutMs(),
                    getTag(),
                    getHeaderParams(),
                    getBodyParams(),
                    response -> this.response = response);
            //Adding request to the queue
            getRequestQueue().add(stringRequest);
        } else {
            VolleyJSONRequest jsonRequest = new VolleyJSONRequest(
                    requestMethod,
                    getUrl(),
                    this::setJSONResponse,
                    this::setErrorResponse,
                    getInitialTimeoutMs(),
                    getTag(),
                    getHeaderParams(),
                    getBodyParams(),
                    response -> this.response = response);
            //Adding request to the queue
            getRequestQueue().add(jsonRequest);
        }
    }

    private void getStringResponse(String response) {
        try {
            Log.e("R: ",response.substring(response.length() - 100));
            String data = response;
            //delete backslashes ( \ ) :
            data = data.replaceAll("[\\\\]{1}[\"]{1}", "\"");
            //delete first and last double quotation ( " ) :
            data = data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1);
            if (response.lastIndexOf("}") != response.length())
                response = response + "}";
            Log.e("R: ",response.substring(response.length() - 100));
            JSONObject json = new JSONObject(data);
            setSuccessResponse(response, json);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response = response.replace("\\\\", "");
                if (response.lastIndexOf("}") != response.length())
                    response = response + "}";
                setSuccessResponse(response, new JSONObject((response.substring(response.indexOf("{")))));
            } catch (Exception e2) {
                e2.printStackTrace();
                response = response + "}";
                setSuccessResponse(response, null);
            }
//            NetworkResponse networkResponse = new NetworkResponse(409, null,false, 0,new ArrayList<>());
//            VolleyError volleyError = new VolleyError(networkResponse);
//            volleyError.initCause(e);
//            setErrorResponse(volleyError);
        }
    }

    private void setJSONResponse(JSONObject jsonObject) {
        setSuccessResponse(jsonObject.toString(), jsonObject);
    }

    private void setSuccessResponse(String string, JSONObject jsonObject) {
        if (isShowProgress())
            hideProgressDialog();

        if (getListener() != null) {
            if (response == null) {
                response = new NetworkResponse(200, null, true, 0, null);
            }

            if (isShowLog()) {
                if (response != null) {
                    showLogMessage("statusCode: " + response.statusCode);
                    showLogMessage("data: " + response.data.toString());
                    showLogMessage("notModified: " + response.notModified);
                    showLogMessage("networkTimeMs: " + response.networkTimeMs);
                    showLogMessage("headers: " + response.headers.toString());
                    showLogMessage("allHeaders: " + response.allHeaders);
                }
                showLogMessage("response: " + string);
            }

            if (string != null && string.length() > 0)
                getListener().onApiStringRequestResponse(
                        new com.omairtech.apirequest.model.NetworkResponse(response),
                        string,
                        getTempId());

            if (jsonObject != null && jsonObject.length() > 0)
                getListener().onApiJSONRequestResponse(
                        new com.omairtech.apirequest.model.NetworkResponse(response),
                        jsonObject,
                        getTempId());
        }
    }


    private void setErrorResponse(VolleyError volleyError) {
        if (isShowLog())
            showLogMessage(volleyError.getMessage());

        if (isShowProgress())
            hideProgressDialog();

        if (getListener() != null) {
            getListener().onApiRequestError(
                    new com.omairtech.apirequest.model.NetworkResponse(volleyError.networkResponse),
                    volleyError.toString(),
                    getTempId());
        }

        String message = getActivity().getString(R.string.connection_error_please_try_again);
        if (volleyError.networkResponse != null && volleyError.getMessage() != null)
            message = String.format(Locale.ENGLISH, "%d: %s", volleyError.networkResponse.statusCode, volleyError.getMessage());

        if (isShowTryAgainIfFails() && !getActivity().isFinishing()) {
            new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.connect, (dialog, which) -> execute())
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }
}
