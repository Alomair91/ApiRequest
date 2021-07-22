package com.omairtech.simple.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.util.ApiLink;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.simple.util.Utils;

import org.json.JSONObject;

public class BaseFragment extends Fragment implements ApiRequestListener {
    protected Utils util;
    protected ApiLink apiLink;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response) {
        util.showLogMessage("onApiStringRequestResponse", response);
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject jsonObject) {
        util.showLogMessage("onApiJSONRequestResponse", jsonObject.toString());
    }

    /**
     * Process errors that does not need to be displayed on screen here
     *
     * @param message String
     */
    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message) {
        util.showLogMessage("onApiRequestError", message);
    }
}
