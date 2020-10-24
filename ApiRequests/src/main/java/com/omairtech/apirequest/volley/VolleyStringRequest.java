package com.omairtech.apirequest.volley;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Hashtable;
import java.util.Map;


public class VolleyStringRequest extends StringRequest {

    private Map<String, String> headers = new Hashtable<>();
    private Map<String, String> params = new Hashtable<>();

    private int initialTimeoutMs;
    private String tag;

    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener
            , int initialTimeoutMs,String tag) {
        super(method, url, listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;

        setDefaults();
    }

    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener
            , int initialTimeoutMs,String tag, Map<String, String> params) {
        super(method, url, listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;
        this.params = params;

        setDefaults();
    }

    private void setDefaults() {
        setRetryPolicy(new DefaultRetryPolicy(
                initialTimeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        setShouldCache(false);
        setTag(tag);
    }

    @Override
    public Map<String, String> getHeaders() /*throws AuthFailureError*/ {
        return headers;
    }

    @Override
    protected Map<String, String> getParams()/* throws AuthFailureError*/ {
        return params;
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        retryPolicy = new DefaultRetryPolicy(
                initialTimeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return super.setRetryPolicy(retryPolicy);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e("TAG", "volleyError " + volleyError.getMessage());
        return super.parseNetworkError(volleyError);
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // Map<String, String> responseHeaders = response.headers;
//        try {
//            String expire = response.headers.get("expires");
//            String date = response.headers.get("Date");
//            Log.e("TAG", "parseNetworkResponse expire " + expire);
//            Log.e("TAG", "parseNetworkResponse date " + date);
//            if (expire != null && expire.length() < 3) {
//                response.headers.put("expires", date);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return super.parseNetworkResponse(response);
    }
}
