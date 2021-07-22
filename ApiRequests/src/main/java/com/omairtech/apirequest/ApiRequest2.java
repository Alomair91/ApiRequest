package com.omairtech.apirequest;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.apirequest.util.Interface.ApiSuccessListener;
import com.omairtech.apirequest.util.enums.RequestType;

import org.json.JSONObject;

import java.util.Map;

public class ApiRequest2<T> extends ApiRequest {

    public Class<T> model;
    public void setModel(Class<T> model) {
        this.model = model;
    }
    public Class<T> getModel() {
        return model;
    }

    public T fromJson(String response) throws Exception {
        return new Gson().fromJson(response, getModel());
    }

    public ApiRequest2(Context context) {
        super(context);
    }

    public ApiRequest2(Context context, Class<T> model) {
        super(context);
        setModel(model);
    }

    public ApiRequest2(Context context, Class<T> model, RequestType requestType, String url) {
        super(context, requestType, url);
        setModel(model);
    }

    public ApiRequest2(Context context, Class<T> model, RequestType requestType, String url, ApiRequestListener listener) {
        super(context, requestType, url, listener);
        setModel(model);
    }

    public ApiRequest2(Context context, Class<T> model, RequestType requestType, String url, ApiRequestListener listener,
                       Map<String, String> body) {
        super(context, requestType, url, listener, body);
        setModel(model);
    }

    public ApiRequest2(Context context, Class<T> model, RequestType requestType, String url, ApiRequestListener listener,
                       Map<String, String> header, Map<String, String> body) {
        super(context, requestType, url, listener, header, body);
        setModel(model);
    }


    public ApiRequest2(Context context, String url, ImageView imageView) {
        super(context, url, imageView);
    }

    public void execute() {
        execute(new ApiSuccessListener() {
            @Override
            public void onResponse(com.omairtech.apirequest.remote.model.NetworkResponse response,
                                   String string, JSONObject jsonObject) {
                setModelResponse(response, string, jsonObject);
            }
        });
    }

    private void setModelResponse(com.omairtech.apirequest.remote.model.NetworkResponse networkResponse,
                                  String string, JSONObject jsonObject) {
        if (getListener() != null && model != null && response != null) {
            try {
                T response = fromJson(jsonObject != null && jsonObject.length() > 0
                        ? jsonObject.toString() : string);
                getListener().onApiRequestResponse(networkResponse, response);
                getListener().onApiRequestResponse(networkResponse, response, getTempId());
            } catch (Exception e) {
                e.printStackTrace();
                errorResponse(new VolleyError().networkResponse,e.getMessage());
            }
        }
    }
}