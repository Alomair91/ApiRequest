package com.omairtech.apirequest.util.Interface;


import com.android.volley.NetworkResponse;
import java.util.Map;

public interface RequestListener {
    void onResponse(NetworkResponse networkResponse, Map<String,Object> map);
}
