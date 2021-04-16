package com.omairtech.simple.Util;


public class ApiLink {

    private final String baseUrl = "https://httpbin.org/";

    private String url(String failed, int id) {
        return baseUrl + failed + (id == 0 ? "" : "/" + id);
    }

    public String get(int id) {
        return url("get", id);
    }


    public String post(int id) {
        return url("post", id);
    }


    public String put(int id) {
        return url("put", id);
    }


    public String delete(int id) {
        return url("delete", id);
    }
}
