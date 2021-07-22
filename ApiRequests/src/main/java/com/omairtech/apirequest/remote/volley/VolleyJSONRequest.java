package com.omairtech.apirequest.remote.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.omairtech.apirequest.util.Interface.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;


public class VolleyJSONRequest extends JsonObjectRequest {


    private final ResponseListener responseListener;

    private Map<String, String> headers;
    private Map<String, String> params = new Hashtable<>();

    private final int initialTimeoutMs;
    private final String tag;

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     *                      parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public VolleyJSONRequest(
            int method,
            String url,
            Response.Listener<JSONObject> listener,
            Response.ErrorListener errorListener,
            int initialTimeoutMs,
            String tag, Map<String,
            String> headers,
            ResponseListener responseListener) {
        super(method, url, new JSONObject(), listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;
        this.headers = headers;
        this.responseListener = responseListener;

        setDefaults();
    }

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     *                      parameters will be posted along with request.
     * @param params        A {@link JSONObject} to post with the request. Null indicates no
     *                      parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public VolleyJSONRequest(
            int method,
            String url,
            Response.Listener<JSONObject> listener,
            Response.ErrorListener errorListener,
            int initialTimeoutMs,
            String tag, Map<String, String> headers,
            Map<String, String> params,
            ResponseListener responseListener) {
        super(method, url, new JSONObject(params != null ? params : new Hashtable<>()), listener, errorListener);

        this.initialTimeoutMs = initialTimeoutMs;
        this.tag = tag;
        this.headers = headers;
        this.params = params;
        this.responseListener = responseListener;

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
        if (headers == null)
            headers = new Hashtable<>();
        return headers;
    }

    @Override
    protected Map<String, String> getParams()/* throws AuthFailureError*/ {
        if (params == null)
            params = new Hashtable<>();
        return params;
    }

//    @Override
//    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//        retryPolicy = new DefaultRetryPolicy(
//                Helper.initialTimeoutMs1,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        return super.setRetryPolicy(retryPolicy);
//    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        // Map<String, String> responseHeaders = response.headers;
//        try {
//            String expire = response.headers.get("expires");
//            String date = response.headers.get("Date");
//            if (expire != null && expire.length() < 3) {
//                response.headers.put("expires", date);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (responseListener != null) {
            responseListener.onNetworkResponse(response);
        }
        try {
            String utf8String = new String(response.data, StandardCharsets.UTF_8);
            return Response.success(new JSONObject(utf8String), HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            // log error
            //return Response.error(new ParseError(e));
            return super.parseNetworkResponse(response);
        }
        //return super.parseNetworkResponse(response);
    }
}
