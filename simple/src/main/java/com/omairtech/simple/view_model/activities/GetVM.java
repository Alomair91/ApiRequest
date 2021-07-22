package com.omairtech.simple.view_model.activities;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.ApiRequest2;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.apirequest.util.enums.InitialTimeout;
import com.omairtech.apirequest.util.enums.RequestType;
import com.omairtech.apirequest.util.enums.ResponseType;
import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.BuildConfig;
import com.omairtech.simple.base.BaseViewModel;
import com.omairtech.simple.base.UserMobel;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class GetVM extends BaseViewModel {


    @Override
    public void init(Context context) {
        super.init(context);
        url.setValue(apiLink.get);
    }

    private boolean checkTextUrl() {
        return url.getValue() != null && !url.getValue().isEmpty();
    }

    public void onBtnClicked(View view) {
        if (checkTextUrl()) {
//            getDataFromServerEx1();
//            getDataFromServerEx2();
            getDataFromServerEx3();
        }
    }

    private void getDataFromServerEx1() {
        createRequest(this, RequestType.GET, url.getValue(), true).execute();
    }

    private void getDataFromServerEx2() {
        ApiRequest request = createRequest(this, RequestType.GET, url.getValue(), true);

        Map<String, String> headers = new Hashtable<>();
        headers.put("Authorization", "Bearer YOUR_TOKEN");
        request.setHeaderParams(headers);

        request.setTempId(1);
        request.setTag("GET_REQUEST");

        request.setResponseType(ResponseType.JSON);
        request.setInitialTimeoutMs(InitialTimeout.Time20Second);

        request.setShowProgressDialog(true);
        request.setShowTryAgainIfFails(true);

        request.execute();
    }

    private void getDataFromServerEx3() {
        ApiRequest2<UserMobel> apiRequest2 = new ApiRequest2<>(context, UserMobel.class);
        apiRequest2.setListener(this);
        apiRequest2.setUrl(url.getValue());
        apiRequest2.setRequestType(RequestType.GET);
        apiRequest2.setShowLog(BuildConfig.DEBUG);
        apiRequest2.execute();

    }


    // String =====================================================================================
    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response) {
        super.onApiStringRequestResponse(networkResponse, response);
    }

    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response, int tempId) {
        super.onApiStringRequestResponse(networkResponse, response,tempId);
    }

    // JSON =======================================================================================
    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response) {
        super.onApiJSONRequestResponse(networkResponse, response);
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response, int tempId) {
        super.onApiJSONRequestResponse(networkResponse, response,tempId);
    }

    // T =======================================================================================
    @Override
    public <T> void onApiRequestResponse(NetworkResponse networkResponse, T response) {
        super.onApiRequestResponse(networkResponse, response);
        if(response instanceof UserMobel){
            Log.d("TAG", "Url: " + ((UserMobel) response).getUrl());
        }
    }

    @Override
    public <T> void onApiRequestResponse(NetworkResponse networkResponse, T response, int tempId) {
        super.onApiRequestResponse(networkResponse, response,tempId);
        if(response instanceof UserMobel){
            Log.d("TAG", "Url: " + ((UserMobel) response).getUrl());
        }
    }

    // Error ======================================================================================
    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message) {
        super.onApiRequestError(networkResponse, message);
    }

    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message, int tempId) {
        super.onApiRequestError(networkResponse, message);
    }
}