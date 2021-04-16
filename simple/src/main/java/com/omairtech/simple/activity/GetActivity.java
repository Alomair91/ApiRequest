package com.omairtech.simple.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.apirequest.enums.InitialTimeout;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.model.NetworkResponse;
import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class GetActivity extends BaseActivity{

    private EditText txt_url;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Get request");

        txt_url = findViewById(R.id.txt_url);
        txt_result = findViewById(R.id.txt_result);

        txt_url.setText(apiLink.get(0));
    }

    private boolean checkTextUrl() {
        return !txt_url.getText().toString().isEmpty();
    }

    public void onBtnClicked(View view) {
        if (checkTextUrl())
//            getDataFromServerEx1();
         getDataFromServerEx2();
    }

    private void getDataFromServerEx1() {
        new ApiRequest(this, this, RequestType.GET, txt_url.getText().toString()).execute();
    }

    private void getDataFromServerEx2() {
        ApiRequest request = new ApiRequest(this);

        request.setListener(this);
        request.setRequestType(RequestType.GET);
        request.setUrl(txt_url.getText().toString());

        Map<String, String> headers = new Hashtable<>();
        headers.put("Authorization", "Bearer YOUR_TOKEN");
        request.setHeaderParams(headers);

        request.setTempId(1);
        request.setTag("GET_REQUEST");

        request.setResponseType(ResponseType.JSON);
        request.setInitialTimeoutMs(InitialTimeout.Time20Second);

        request.setShowProgressDialog(true);
        request.setShowTryAgainIfFails(true);
        request.setShowLog(true);

        request.execute();
    }

    // String =====================================================================================
    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response) {
        super.onApiStringRequestResponse(networkResponse, response);
        setToTextView(response);
    }

    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response, int tempId) {
        super.onApiStringRequestResponse(networkResponse, response);
        setToTextView(response);
    }

    // JSON =======================================================================================
    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response) {
        super.onApiJSONRequestResponse(networkResponse, response);
        setToTextView(response.toString());
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response, int tempId) {
        super.onApiJSONRequestResponse(networkResponse, response);
        setToTextView(response.toString());
    }

    // Error ======================================================================================
    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message) {
        super.onApiRequestError(networkResponse, message);
        setToTextView(message);
    }

    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message, int tempId) {
        super.onApiRequestError(networkResponse, message);
        setToTextView(message);
    }

    private void setToTextView(String message) {
        txt_result.setText(message);
    }
}