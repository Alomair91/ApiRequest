package com.omairtech.simple.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.omairtech.simple.Utils.ApiLink;
import com.omairtech.simple.Utils.Helper;
import com.omairtech.apirequest.Interface.ApiInterface;

public class BaseDialog extends DialogFragment implements ApiInterface {
    protected ApiLink apiLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiLink = new ApiLink();
    }

    public boolean isInternetAvailable() {
        return Helper.isInternetAvailable(getActivity());
    }

    public void showAlertDialog(String message) {
        Helper.showAlert(getContext(), message);
    }

    public void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }


    public void showLogMessage(String tag, String message) {
        Helper.showLogMessage(tag, message);
    }
}
