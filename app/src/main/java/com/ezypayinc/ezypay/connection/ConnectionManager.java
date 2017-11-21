package com.ezypayinc.ezypay.connection;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


class ConnectionManager {

    private static final String BASIC_URL = "http://192.168.1.104:8080/";
    //private static final String BASIC_URL = "https://ugwo-platform.appspot.com/";
    private Context mContext;

    ConnectionManager() {
        mContext = EzyPayApplication.getInstance();
    }

    void sendCustomRequest(int httpMethod, String url, JSONObject parameters, final HashMap<String, String> headers,
                           Response.Listener<JsonElement> successHandler, Response.ErrorListener errorHandler) {
        url = BASIC_URL + url;
        CustomRequest request = new CustomRequest(httpMethod,url, parameters, headers,
                successHandler, errorHandler);
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);
    }

    void sendMultiPartRequest(int httpMethod, String url, final byte[] image, final HashMap<String, String> headers,
                              Response.Listener<NetworkResponse> successHandler, Response.ErrorListener errorHandler) {
        url = BASIC_URL + url;
        MultipartRequest request = new MultipartRequest(httpMethod, url, headers, successHandler, errorHandler) {

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart("image","image/jpeg", image));
                return params;
            }
        };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);
    }
}
