package com.omairtech.apirequest.Interface;

import org.json.JSONObject;

public interface ApiRequestInterface {

    default void onServerResponse(String response) {
    }
    default void onServerResponse(JSONObject jsonObject) {
    }

    default void onServerResponse(String response, int tempId) {
    }
    default void onServerResponse(JSONObject jsonObject, int tempId) {
    }

    default void onServerError(String message) {
    }
    default void onServerError(String message, int tempId) {
    }
}
