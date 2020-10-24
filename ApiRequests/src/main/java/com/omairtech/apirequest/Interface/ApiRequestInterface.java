package com.omairtech.apirequest.Interface;

import org.json.JSONObject;

public interface ApiRequestInterface {

    default void onApiRequestResponse(String response) {
    }
    default void onApiRequestResponse(JSONObject jsonObject) {
    }

    default void onApiRequestResponse(String response, int tempId) {
    }
    default void onApiRequestResponse(JSONObject jsonObject, int tempId) {
    }

    default void onApiRequestError(String message) {
    }
    default void onApiRequestError(String message, int tempId) {
    }
}
