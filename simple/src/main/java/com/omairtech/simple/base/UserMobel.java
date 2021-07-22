package com.omairtech.simple.base;


import java.util.Map;

public class UserMobel {
    Object args;
    Map<String,String> headers;
    String origin;
    String url;

    public UserMobel(Object args, Map<String, String> headers, String origin, String url) {
        this.args = args;
        this.headers = headers;
        this.origin = origin;
        this.url = url;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
