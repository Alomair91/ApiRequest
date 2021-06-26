package com.omairtech.simple.view_model.activities;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.enums.InitialTimeout;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.model.NetworkResponse;
import com.omairtech.simple.base.BaseViewModel;

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
            getDataFromServerEx2();
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


    // String =====================================================================================
    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response) {
        super.onApiStringRequestResponse(networkResponse, response);
    }

    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response, int tempId) {
        super.onApiStringRequestResponse(networkResponse, response);
    }

    // JSON =======================================================================================
    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response) {
        super.onApiJSONRequestResponse(networkResponse, response);
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response, int tempId) {
        super.onApiJSONRequestResponse(networkResponse, response);
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