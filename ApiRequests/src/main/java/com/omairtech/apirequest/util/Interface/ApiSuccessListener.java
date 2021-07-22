package com.omairtech.apirequest.util.Interface;


import com.omairtech.apirequest.remote.model.NetworkResponse;
import org.json.JSONObject;

public interface ApiSuccessListener {

    default void onResponse(NetworkResponse networkResponse, String string, JSONObject jsonObject) {
    }

}
