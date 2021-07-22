package com.omairtech.apirequest.remote.model;

import android.content.Context;

import com.android.volley.toolbox.Volley;

public class RequestQueue {

    private static volatile com.android.volley.RequestQueue requestQueue;
    public static com.android.volley.RequestQueue getInstance(Context context) {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }
}
