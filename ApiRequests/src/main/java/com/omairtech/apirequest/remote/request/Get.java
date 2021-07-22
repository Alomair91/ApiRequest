package com.omairtech.apirequest.remote.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.remote.model.RequestQueue;
import com.omairtech.apirequest.remote.volley.VolleyJSONRequest;
import com.omairtech.apirequest.remote.volley.VolleyStringRequest;
import com.omairtech.apirequest.util.Interface.ResponseListener;

import org.json.JSONObject;

public class Get {

    private final BaseHelper base;

    public Get(BaseHelper base) {
        this.base = base;
    }

    public void StringRequest(Context context,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener,
                              ResponseListener responseListener) {

        VolleyStringRequest stringRequest = new VolleyStringRequest(
                Request.Method.GET,
                base.getUrl(),
                listener,
                errorListener,
                base.getInitialTimeoutMs(),
                base.getTag(),
                base.getHeaderParams(),
                responseListener);
        //Adding request to the queue
        RequestQueue.getInstance(context).add(stringRequest);
    }

    public void JsonRequest(Context context,
                            Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener,
                            ResponseListener responseListener) {
        VolleyJSONRequest jsonRequest = new VolleyJSONRequest(
                Request.Method.GET,
                base.getUrl(),
                listener,
                errorListener,
                base.getInitialTimeoutMs(),
                base.getTag(),
                base.getHeaderParams(),
                responseListener);
        //Adding request to the queue
        RequestQueue.getInstance(context).add(jsonRequest);
    }

}
