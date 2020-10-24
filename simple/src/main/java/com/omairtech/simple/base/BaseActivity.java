package com.omairtech.simple.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omairtech.simple.Utils.ApiLink;
import com.omairtech.simple.Utils.Helper;
import com.omairtech.apirequest.Interface.ApiRequestInterface;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements ApiRequestInterface {
    protected ApiLink apiLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiLink = new ApiLink();
    }

    public boolean isInternetAvailable() {
        return Helper.isInternetAvailable(this) && apiLink != null;
    }

    protected void showAlertDialog(String message) {
        Helper.showAlert(this, message);
    }

    protected void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToast(int messageId) {
        Toast.makeText(this, messageId, Toast.LENGTH_LONG).show();
    }

    public void showLogMessage(String tag, String message) {
        Helper.showLogMessage(tag, message);
    }
}
