package com.omairtech.simple.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.omairtech.simple.Util.ApiLink;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.simple.Util.Utils;

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
    public void onApiRequestResponse(String response) {
        util.showLogMessage("OnApiRequestResponse", response);
    }

    /**
     * Process errors that does not need to be displayed on screen here
     *
     * @param message String
     */
    @Override
    public void onApiRequestError(String message) {
        util.showLogMessage("onApiRequestError", message);
    }
}
