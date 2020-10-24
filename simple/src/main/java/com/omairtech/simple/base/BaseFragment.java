package com.omairtech.simple.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omairtech.simple.Utils.ApiLink;
import com.omairtech.simple.Utils.Helper;
import com.omairtech.apirequest.Interface.ApiInterface;

public class BaseFragment extends Fragment implements ApiInterface {
    protected ApiLink apiLink;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
