package com.omairtech.simple.base;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.util.Interface.ApiRequestListener;
import com.omairtech.apirequest.util.enums.InitialTimeout;
import com.omairtech.apirequest.util.enums.RequestType;
import com.omairtech.apirequest.util.enums.ResponseType;
import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.BuildConfig;
import com.omairtech.simple.util.ApiLink;
import com.omairtech.simple.util.Utils;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import static com.omairtech.simple.util.App.getApplication;


public class BaseViewModel extends ViewModel implements ApiRequestListener {

    protected Utils util;
    protected ApiLink apiLink;
    protected Context context;

    public MutableLiveData<String> url = new MutableLiveData<>();
    public MutableLiveData<String> result = new MutableLiveData<>();
    public MutableLiveData<String> event = new MutableLiveData<>();

    public BaseViewModel(){
    }
    public BaseViewModel(Context context){
        init(context);
    }

    public void init(Context context){
        this.context =context;
        util = Utils.getInstance(getApplication());
        apiLink = new ApiLink();
    }


    /**
     * Create new ApiRequest
     *
     * @param requestType  method type
     * @param url          full url
     * @param params       any data you want to post
     * @param showProgress boolean
     * @return new ApiRequest
     */
    public ApiRequest createRequest(ApiRequestListener listener, RequestType requestType
            , String url, Map<String, String> params, boolean showProgress) {

        ApiRequest request = new ApiRequest(context);

        request.setListener(listener);
        request.setRequestType(requestType);
        request.setBaseUrl(apiLink.baseUrl);
        request.setUrl(url);


        Map<String, String> headers = new Hashtable<>();
        headers.put("Authorization", "Bearer YOUR_TOKEN");
        headers.put("lang", "en");
        request.setHeaderParams(headers);

        request.setBodyParams(params);


        request.setTempId(1);
        request.setTag("GET_REQUEST");

//        request.setTempId(1);
//        request.setTag("DELETE_REQUEST");

        request.setResponseType(ResponseType.JSON);
        request.setInitialTimeoutMs(InitialTimeout.Time20Second);


        request.setShowProgressDialog(true);
        request.setShowTryAgainIfFails(true);

        request.setShowProgressDialog(showProgress);
        request.setShowTryAgainIfFails(true);
        request.setShowLog(BuildConfig.DEBUG);

        return request;
    }

    public ApiRequest createRequest(ApiRequestListener listener, RequestType requestType
            , String url, boolean showProgress) {
        return createRequest(listener, requestType, url, null, showProgress);
    }



    // String Response =====================================================================================
    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response) {
        util.showLogMessage("onApiStringRequestResponse 1", networkResponse.statusCode +": "+ response);
        setToTextView(networkResponse.statusCode,response);
    }

    @Override
    public void onApiStringRequestResponse(NetworkResponse networkResponse, String response, int tempId) {
        util.showLogMessage("onApiStringRequestResponse 2", networkResponse.statusCode +": "+ response);
        setToTextView(networkResponse.statusCode,response);
    }

    // JSON Response =======================================================================================
    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response) {
        util.showLogMessage("onApiJSONRequestResponse 1", networkResponse.statusCode +": "+ response.toString());
        setToTextView(networkResponse.statusCode,response.toString());
    }

    @Override
    public void onApiJSONRequestResponse(NetworkResponse networkResponse, JSONObject response, int tempId) {
        util.showLogMessage("onApiJSONRequestResponse 2", networkResponse.statusCode +": "+ response.toString());
        setToTextView(networkResponse.statusCode,response.toString());
    }

    @Override
    public <T> void onApiRequestResponse(NetworkResponse networkResponse, T response) {
        util.showLogMessage("onApiRequestResponse 1", networkResponse.statusCode +": "+ response.toString());
        setToTextView(networkResponse.statusCode,response.toString());
    }

    @Override
    public <T> void onApiRequestResponse(NetworkResponse networkResponse, T response, int tempId) {
        util.showLogMessage("onApiRequestResponse 2", networkResponse.statusCode +": "+ response.toString());
        setToTextView(networkResponse.statusCode,response.toString());

    }

    // Error Response ======================================================================================
    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message) {
        util.showLogMessage("onApiRequestError", networkResponse.statusCode +": "+ message);
        setToTextView(networkResponse.statusCode,message);
    }

    @Override
    public void onApiRequestError(NetworkResponse networkResponse, String message, int tempId) {
        util.showLogMessage("onApiRequestError", networkResponse.statusCode +": "+ message);
        setToTextView(networkResponse.statusCode,message);
    }


    public void setToTextView(int code, String message) {
        result.setValue(String.format(Locale.ENGLISH,"Status code: %d\n\nResult:\n%s", code, message));
    }
}
