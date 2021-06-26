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

public class PutVM extends BaseViewModel {

    @Override
    public void init(Context context) {
        super.init(context);
        url.setValue(apiLink.put);
    }

    private boolean checkTextUrl() {
        return url.getValue() != null && !url.getValue().isEmpty();
    }

    public void onBtnClicked(View view) {
        if (checkTextUrl()) {
            putDataFromServerEx1();
        }
    }


    private void putDataFromServerEx1() {
        ApiRequest request = createRequest(this, RequestType.PUT, url.getValue(), true);

        request.setListener(this);
        request.setRequestType(RequestType.PUT);
        request.setSetPUTAndDELETEAsPOST(true);

        Map<String, String> headersParams = new Hashtable<>();
        headersParams.put("Authorization", "Bearer YOUR_TOKEN");
        request.setHeaderParams(headersParams);

        Map<String, String> bodyParams = new Hashtable<>();
        bodyParams.put("email", "test@test.com");
        bodyParams.put("password", "123456");
        request.setBodyParams(bodyParams);

        request.setTempId(1);
        request.setTag("PUT_REQUEST");

        request.setResponseType(ResponseType.STRING);
        request.setInitialTimeoutMs(InitialTimeout.Time50Second);

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