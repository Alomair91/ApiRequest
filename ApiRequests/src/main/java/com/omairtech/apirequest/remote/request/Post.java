package com.omairtech.apirequest.remote.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.omairtech.apirequest.Base.BaseHelper;
import com.omairtech.apirequest.remote.model.RequestQueue;
import com.omairtech.apirequest.remote.volley.VolleyJSONRequest;
import com.omairtech.apirequest.remote.volley.VolleyStringRequest;
import com.omairtech.apirequest.util.Interface.ResponseListener;
import com.omairtech.apirequest.util.enums.RequestType;

import org.json.JSONObject;

import java.util.Map;

public class Post {

    private final BaseHelper base;

    public Post(BaseHelper base) {
        this.base = base;

        if (this.base.isSetPUTAndDELETEAsPOST()) {
            Map<String, String> bodyParams = this.base.getBodyParams();
            switch (this.base.getRequestType()) {
                case POST:
                    bodyParams.put("_method", "post");
                    break;
                case PUT:
                    bodyParams.put("_method", "put");
                    break;
                case DELETE:
                    bodyParams.put("_method", "delete");
                    break;
            }
            this.base.setBodyParams(bodyParams);
        }
    }

    public void StringRequest(Context context,
                              Response.Listener<String> listener,
                              Response.ErrorListener errorListener,
                              ResponseListener responseListener) {

        VolleyStringRequest stringRequest = new VolleyStringRequest(
                getRequestMethod(),
                base.getUrl(),
                listener,
                errorListener,
                base.getInitialTimeoutMs(),
                base.getTag(),
                base.getHeaderParams(),
                base.getBodyParams(),
                responseListener);
        //Adding request to the queue
        RequestQueue.getInstance(context).add(stringRequest);
    }

    public void JsonRequest(Context context,
                            Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener,
                            ResponseListener responseListener) {
        VolleyJSONRequest jsonRequest = new VolleyJSONRequest(
                getRequestMethod(),
                base.getUrl(),
                listener,
                errorListener,
                base.getInitialTimeoutMs(),
                base.getTag(),
                base.getHeaderParams(),
                base.getBodyParams(),
                responseListener);
        //Adding request to the queue
        RequestQueue.getInstance(context).add(jsonRequest);
    }

    private int getRequestMethod(){
        if (base.isSetPUTAndDELETEAsPOST()) {
            if (base.getRequestType() == RequestType.PUT)
                return Request.Method.PUT;
            else if (base.getRequestType() == RequestType.DELETE)
                return Request.Method.DELETE;
        }
        return Request.Method.POST;
    }
}
