package com.omairtech.simple.base;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.util.ApiLink;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.simple.util.Utils;

import org.json.JSONObject;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements ApiRequestListener {
    protected Utils util;
    protected ApiLink apiLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = Utils.getInstance(getApplication());
        apiLink = new ApiLink();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * Process data that does not need to be displayed on screen here
     *
     * @param response String
     */
    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse,String response) {
        util.showLogMessage("onApiStringRequestResponse",networkResponse.statusCode +": "+ response);
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject jsonObject) {
        util.showLogMessage("onApiJSONRequestResponse", networkResponse.statusCode +": "+ jsonObject.toString());
    }

    /**
     * Process errors that does not need to be displayed on screen here
     *
     * @param message String
     */
    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message) {
        util.showLogMessage("onApiRequestError", networkResponse.statusCode +": "+ message);
    }
}
