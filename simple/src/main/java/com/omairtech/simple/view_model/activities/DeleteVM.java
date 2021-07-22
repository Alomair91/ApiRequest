package com.omairtech.simple.view_model.activities;

import android.content.Context;
import android.view.View;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.util.enums.InitialTimeout;
import com.omairtech.apirequest.util.enums.RequestType;
import com.omairtech.apirequest.util.enums.ResponseType;
import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.base.BaseViewModel;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class DeleteVM extends BaseViewModel {

    @Override
    public void init(Context context) {
        super.init(context);
        url.setValue(apiLink.delete);
    }

    private boolean checkTextUrl() {
        return url.getValue()!= null && !url.getValue().isEmpty();
    }

    public void onBtnClicked(View view) {
        if (checkTextUrl()){
            deleteDataFromServerEx1();
        }
    }


    private void deleteDataFromServerEx1() {
        ApiRequest request = createRequest(this, RequestType.DELETE, url.getValue(), true);

        request.setListener(this);
        request.setRequestType(RequestType.DELETE);

        Map<String, String> headersParams = new Hashtable<>();
        headersParams.put("Authorization", "Bearer YOUR_TOKEN");
        request.setHeaderParams(headersParams);

        request.setTempId(1);
        request.setTag("DELETE_REQUEST");

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