package com.omairtech.simple.Utils;

import java.util.Date;

public class ApiLink {

    private String baseUrl = "https://httpbin.org/";

    private String url(String failed, int id) {
        return baseUrl + failed + (id == 0 ? "" : "/" + id) + "?timestamp=" + (new Date()).getTime();
    }

    /**
     * Users RESTFul API: GET , POST , PUT , DELETE
     */
    public String get(int id) {
        return url("get", id);
    }
}
