package com.omairtech.apirequest;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.volley.VolleyJSONRequest;
import com.omairtech.apirequest.volley.VolleyStringRequest;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

public class ApiRequest2<T> extends BaseHelper {



    public ApiRequest2(Context context) {
        setContext(context);
    }

    public ApiRequest2(Context context,Class<T> model) {
        setContext(context);
        setModel(model);
    }

    public ApiRequest2(Context context,
                       String url, ImageView imageView) {
        setContext(context);
        getImage(url, imageView);
    }

    public ApiRequest2(Context context,
                       ApiRequestListener listener) {
        setContext(context);
        setListener(listener);
    }

    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType) {
        setContext(context);
        setListener(listener);
        setRequestType(requestType);
    }

    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url) {
        setContext(context);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
    }

    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url,
                       Map<String, String> header) {

        setContext(context);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
    }


    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url,
                       Map<String, String> header,
                       Map<String, String> body) {

        setContext(context);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
    }

    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url,
                       Map<String, String> header,
                       Map<String, String> body,
                       boolean showProgress) {
        setContext(context);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
        setShowProgressDialog(showProgress);
    }


    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url,
                       Map<String, String> header,
                       Map<String, String> body,
                       boolean showProgress,
                       int tempId) {
        setContext(context);
        setListener(listener);
        setRequestType(requestType);
        setUrl(url);
        setHeaderParams(header);
        setBodyParams(body);
        setShowProgressDialog(showProgress);
        setTempId(tempId);
    }


    public ApiRequest2(Context context,
                       ApiRequestListener listener,
                       RequestType requestType,
                       String url,
                       Map<String, String> header,
                       Map<String, String> body,
                       int initialTimeoutMs) {
        setContext(context);
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
            requestQueue = Volley.newRequestQueue(getContext());
        return requestQueue;
    }

    private void getImage(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest(url, imageView::setImageBitmap
                , 0, 0, null, Bitmap.Config.RGB_565
                , error -> imageView.setImageResource(R.drawable.ic_baseline_broken_image_24));
        getRequestQueue().add(request);
    }

    private void getDataFromServer() {
        if (getResponseType() == ResponseType.STRING) {
            VolleyStringRequest stringRequest = new VolleyStringRequest(
                    Request.Method.GET,
                    getUrl(),
                    this::setStringResponse,
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
                    this::setStringResponse,
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


    private void setStringResponse(String response) {
        try {
            showLogMessage(response.substring(response.length() - 100));
            String data = response;
            //delete backslashes ( \ ) :
            data = data.replaceAll("[\\\\]{1}[\"]{1}", "\"");
            //delete first and last double quotation ( " ) :
            data = data.substring(data.indexOf("{"), data.lastIndexOf("}") + 1);
            if (response.lastIndexOf("}") != response.length())
                response = response + "}";
            showLogMessage(response.substring(response.length() - 100));
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


            if (model != null)
                getListener().onApiRequestResponse(
                        new com.omairtech.apirequest.model.NetworkResponse(response),
                        fromJson(jsonObject != null && jsonObject.length() > 0? jsonObject.toString() : string),
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

        String message = getContext().getString(R.string.connection_error_please_try_again);
        if (volleyError.networkResponse != null && volleyError.getMessage() != null)
            message = String.format(Locale.ENGLISH, "%d: %s", volleyError.networkResponse.statusCode, volleyError.getMessage());

        if (isShowTryAgainIfFails() && isActivityRunning()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.connect, (dialog, which) -> execute())
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        }
    }


    private Class<T> model;
    public void setModel(Class<T> model) {
        this.model = model;
    }
    public Class<T> getModel() {
        return model;
    }
    public T fromJson(String response) {
        T list = new Gson().fromJson(response, getModel());
        showLogMessage(response);
        showLogMessage("T: " + list);
        return list;
    }
}