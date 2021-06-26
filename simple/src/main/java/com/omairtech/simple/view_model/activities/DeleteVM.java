package com.omairtech.simple.view_model.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.enums.InitialTimeout;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.model.NetworkResponse;
import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.base.BaseViewModel;
import com.omairtech.simple.databinding.ActivityGetBinding;

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