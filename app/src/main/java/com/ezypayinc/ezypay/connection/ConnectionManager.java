package com.ezypayinc.ezypay.connection;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.HashMap;


class ConnectionManager {

    private static final String BASIC_URL = "http://192.168.1.101:3000/";
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

}
