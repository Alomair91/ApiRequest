package com.omairtech.simple.base;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.omairtech.simple.Util.ApiLink;
import com.omairtech.apirequest.Interface.ApiRequestListener;
import com.omairtech.simple.Util.Utils;

public class BaseDialog extends DialogFragment implements ApiRequestListener {
    protected Utils util;
    protected ApiLink apiLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = Utils.getInstance(getActivity().getApplication());
        apiLink = new ApiLink();
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
