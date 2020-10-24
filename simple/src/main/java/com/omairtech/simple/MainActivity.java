package com.omairtech.simple;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.omairtech.simple.base.BaseActivity;
import com.omairtech.apirequest.ApiRequest;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends BaseActivity{

    private EditText txt_url;
    private TextView txt_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_url = findViewById(R.id.txt_url);
        txt_result = findViewById(R.id.txt_result);

        txt_url.setText(apiLink.get(0));
    }

    private boolean checkTextUrl() {
        return !txt_url.getText().toString().isEmpty();
    }

    // Get Data ================================================================
    public void getDataFromApi(View view) {
        if (checkTextUrl())
            getDataFromServerEx1();
        // getDataFromServerEx2();
    }

    private void getDataFromServerEx1() {
        new ApiRequest(this, this, ApiRequest.GET, txt_url.getText().toString()).execute();
    }
    private void getDataFromServerEx2() {
        ApiRequest apiRequest = new ApiRequest(this);

        apiRequest.setApiInterface(this);
        apiRequest.setRequestMethod(ApiRequest.GET);
        apiRequest.setInitialTimeoutMs(ApiRequest.initialTimeoutMs50C);

        Map<String, String> headers = new Hashtable<>();
        headers.put("Authorization", "Bearer YOUR_TOKEN");
        apiRequest.setHeaderParams(headers);

        apiRequest.setRequestType(ApiRequest.REQUEST_TYPE_JSON);
        apiRequest.setShowProgressDialog(false);
        apiRequest.setUrl(txt_url.getText().toString());
        apiRequest.setTempId(1);

        apiRequest.execute();
    }


    // Post Data ================================================================
    public void postDataFromApi(View view) {
        postDataFromServerEx1();
    }
    private void postDataFromServerEx1() {
        ApiRequest apiRequest = new ApiRequest(this);

        apiRequest.setApiInterface(this);
        apiRequest.setRequestMethod(ApiRequest.POST);

        Map<String, String> headersParams = new Hashtable<>();
        headersParams.put("Authorization", "Bearer YOUR_TOKEN");
        apiRequest.setHeaderParams(headersParams);

        Map<String, String> bodyParams = new Hashtable<>();
        bodyParams.put("email", "test@test.com");
        bodyParams.put("password", "123456");
        apiRequest.setBodyParams(bodyParams);

        apiRequest.setRequestType(ApiRequest.REQUEST_TYPE_JSON);
        apiRequest.setShowProgressDialog(true);
        apiRequest.setUrl(txt_url.getText().toString());

        apiRequest.setShowResendAgainDialog(false);
        apiRequest.setShowLog(true);

        apiRequest.execute();
    }



    @Override
    public void onServerResponse(String response) {
        Log.d("OnServerResponse", response);
        txt_result.setText(response);
    }

    @Override
    public void onServerResponse(JSONObject jsonObject) {
        Log.d("OnServerResponse", jsonObject.toString());
        txt_result.setText(jsonObject.toString());
    }


    @Override
    public void onServerResponse(String response, int tempId) {
        Log.d("OnServerResponse", response);
        txt_result.setText(response);
    }

    @Override
    public void onServerResponse(JSONObject jsonObject, int tempId) {
        Log.d("OnServerResponse", jsonObject.toString());
        txt_result.setText(jsonObject.toString());
    }

    @Override
    public void onServerError(String message) {
        Log.d("OnServerError", message + " ");
        txt_result.setText(message);
    }

    @Override
    public void onServerError(String message, int tempId) {
        Log.d("OnServerError", message + " ");
        txt_result.setText(message);
    }

}