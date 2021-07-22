package com.omairtech.apirequest;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.remote.request.LoadImage;
import com.omairtech.apirequest.remote.volley.VolleyRequest;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.apirequest.util.Interface.ApiSuccessListener;
import com.omairtech.apirequest.util.enums.RequestType;
import com.omairtech.apirequest.view.AlertDialog;
import com.omairtech.apirequest.view.ProgressDialog;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApiRequest extends BaseHelper {

    public ApiRequest(Context context) {
        super(context);
        setContext(context);
    }

    public ApiRequest(Context context, RequestType requestType, String url) {
        super(context);
        setRequestType(requestType);
        setUrl(url);
    }

    public ApiRequest(Context context, RequestType requestType, String url, ApiRequestListener listener) {
        super(context);
        setRequestType(requestType);
        setUrl(url);
        setListener(listener);
    }

    public ApiRequest(Context context, RequestType requestType, String url, ApiRequestListener listener,
                      Map<String, String> body) {
        super(context);
        setRequestType(requestType);
        setUrl(url);
        setListener(listener);
        setBodyParams(body);
    }

    public ApiRequest(Context context, RequestType requestType, String url, ApiRequestListener listener,
                      Map<String, String> header,
                      Map<String, String> body) {
        super(context);
        setRequestType(requestType);
        setUrl(url);
        setListener(listener);
        setHeaderParams(header);
        setBodyParams(body);
    }


    public ApiRequest(Context context, String url, ImageView imageView) {
        super(context);
        new LoadImage(context, url, imageView);
    }


    public ApiSuccessListener apiSuccessListener;

    public void execute() {
        request();
    }

    public void execute(ApiSuccessListener apiSuccessListener) {
        this.apiSuccessListener = apiSuccessListener;
        request();
    }

    public NetworkResponse response;

    private void request() {
        if (isShowProgress())
            ProgressDialog.getInstance().show(getContext(), R.string.loading);

        Observable
                .create((ObservableOnSubscribe<Map<String, Object>>) emitter -> new VolleyRequest(ApiRequest.this, (networkResponse, map) -> {
                    ApiRequest.this.response = networkResponse;
                    emitter.onNext(map);
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(map -> {
                    Log.d(TAG, "upStream: " + map.toString());
                    Log.d(TAG, "upStream Thread: " + Thread.currentThread().getName());
                })
                .subscribe(new Observer<Map<String, Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Map<String, Object> map) {
                        Log.d(TAG, "downStream Thread: " + Thread.currentThread().getName());

                        if (map.containsKey(VolleyRequest.TAG_ERROR)) {
                            errorResponse(response, (String) map.get(VolleyRequest.TAG_ERROR));
                        } else {
                            successResponse(response,
                                    (String) map.get(VolleyRequest.TAG_STRING),
                                    (JSONObject) map.get(VolleyRequest.TAG_JSON));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void successResponse(NetworkResponse response, String string, JSONObject jsonObject) {
        if (isShowProgress())
            ProgressDialog.getInstance().hide();

        if (getListener() != null) {
            if (response == null) {
                response = new NetworkResponse(200, null, true,
                        0, null);
            }

            com.omairtech.apirequest.remote.model.NetworkResponse networkResponse
                    = new com.omairtech.apirequest.remote.model.NetworkResponse(response);

            // Model response
            if (apiSuccessListener != null)
                apiSuccessListener.onResponse(networkResponse, string, jsonObject);

            // JSON response
            if (jsonObject != null && jsonObject.length() > 0) {
                getListener().onApiJSONRequestResponse(networkResponse, jsonObject);
                getListener().onApiJSONRequestResponse(networkResponse, jsonObject, getTempId());
            }

            // String response
            if (string != null && string.length() > 0) {
                getListener().onApiStringRequestResponse(networkResponse, string);
                getListener().onApiStringRequestResponse(networkResponse, string, getTempId());
            }
        }
    }

    public void errorResponse(NetworkResponse response, String error) {
        if (isShowProgress())
            ProgressDialog.getInstance().hide();

        if (getListener() != null) {
            com.omairtech.apirequest.remote.model.NetworkResponse networkResponse
                    = new com.omairtech.apirequest.remote.model.NetworkResponse(response);
            getListener().onApiRequestError(networkResponse, error);
            getListener().onApiRequestError(networkResponse, error, getTempId());
        }

        if (isShowTryAgainIfFails()) {
            AlertDialog.getInstance().show(getContext(), error, tryAgain -> execute());
        }
    }
}
