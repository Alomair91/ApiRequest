package com.omairtech.apirequest.remote.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.R;
import com.omairtech.apirequest.remote.request.Get;
import com.omairtech.apirequest.remote.request.Post;
import com.omairtech.apirequest.util.Interface.RequestListener;
import com.omairtech.apirequest.util.converter.Converter;
import com.omairtech.apirequest.util.enums.ResponseType;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;


public class VolleyRequest {
    public static final String TAG_STRING = "string";
    public static final String TAG_JSON = "json";
    public static final String TAG_ERROR = "error";



    public NetworkResponse response;
    private final BaseHelper base;
    private final RequestListener requestListener;

    public VolleyRequest(BaseHelper base, RequestListener requestListener) {
        this.base = base;
        this.requestListener = requestListener;
        request();
    }

    public void request() {
        showRequestLog();

        switch (base.getRequestType()) {
            case POST:
            case PUT:
            case DELETE:
                if (base.getResponseType() == ResponseType.STRING) {
                    new Post(base).StringRequest(base.getContext(),
                            this::setStringResponse,
                            this::errorResponse,
                            response -> this.response = response);
                } else {
                    new Post(base).JsonRequest(base.getContext(),
                            this::setJSONResponse,
                            this::errorResponse,
                            response -> this.response = response);

                }
                break;
            case GET:
            default:
                if (base.getResponseType() == ResponseType.STRING) {
                    new Get(base).StringRequest(base.getContext(),
                            this::setStringResponse,
                            this::errorResponse,
                            response -> this.response = response);
                } else {
                    new Get(base).JsonRequest(base.getContext(),
                            this::setJSONResponse,
                            this::errorResponse,
                            response -> this.response = response);

                }
                break;
        }
    }

    private void setStringResponse(String response) {
        successResponse(response, Converter.getInstance().stringJson(response));
    }

    private void setJSONResponse(JSONObject jsonObject) {
        successResponse(jsonObject.toString(), jsonObject);
    }

    public void successResponse(String string, JSONObject jsonObject) {
        Map<String, Object> map = new Hashtable<>();
        map.put(TAG_STRING,string);
        map.put(TAG_JSON, jsonObject);
        requestListener.onResponse(response,map);
        showResponseLog(string);
    }

    public void errorResponse(VolleyError volleyError) {
        if (base.isShowLog())
            base.showLog(volleyError.getMessage());

        String message = base.getContext().getString(R.string.connection_error_please_try_again);
        if (volleyError.networkResponse != null && volleyError.getMessage() != null)
            message = String.format(Locale.ENGLISH, "%d: %s",
                    volleyError.networkResponse.statusCode, volleyError.getMessage());

        Map<String, Object> map = new Hashtable<>();
        map.put(TAG_ERROR,message);

        requestListener.onResponse(volleyError.networkResponse,map);
    }

    public void showRequestLog() {
        if (base.isShowLog()) {
            base.showLog("URL: " + base.getUrl());
            base.showLog("Request type: " + base.getRequestType());
            base.showLog("Response type: " + base.getResponseType());
            base.showLog("Header params: " +base. getHeaderParams().toString());
            base.showLog("Body params: " + base.getBodyParams().toString());
            base.showLog("========================================");
        }
    }

    public void showResponseLog(String string) {
        if (base.isShowLog()) {
            if (response != null) {
                base.showLog("statusCode: " + response.statusCode);
                base.showLog("data: " + Arrays.toString(response.data));
                base.showLog("notModified: " + response.notModified);
                base.showLog("networkTimeMs: " + response.networkTimeMs);
                if (response.headers != null)
                    base.showLog("headers: " + response.headers.toString());
                base.showLog("allHeaders: " + response.allHeaders);
            }
            base.showLog("response: " + string);
            base.showLog("========================================");
        }
    }
}
