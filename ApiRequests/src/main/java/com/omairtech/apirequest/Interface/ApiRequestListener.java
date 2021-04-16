package com.omairtech.apirequest.Interface;

import com.omairtech.apirequest.model.NetworkResponse;

import org.json.JSONObject;

public interface ApiRequestListener {

    default void onApiStringRequestResponse(NetworkResponse networkResponse,String response) {
    }
    default void onApiStringRequestResponse(NetworkResponse networkResponse, String response, int tempId) {
        onApiStringRequestResponse(networkResponse,response);
    }


    default void onApiJSONRequestResponse(NetworkResponse networkResponse,JSONObject response) {
    }
    default void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response, int tempId) {
        onApiJSONRequestResponse(networkResponse,response);
    }


    default void onApiRequestError(NetworkResponse networkResponse, String message) {
    }
    default void onApiRequestError(NetworkResponse networkResponse, String message, int tempId) {
        onApiRequestError(networkResponse,message);
    }
}
