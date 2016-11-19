package com.ezypayinc.ezypay.connection;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustavoquesada on 11/4/16.
 */

public class ConnectionManager {

    private static final String BASIC_URL = "http://192.168.1.101:3000/";
    private Context mContext;

    public ConnectionManager(Context context) {
        mContext = context;
    }


        public void sendRequest(int httpMethod, String url, JSONObject parameters, final HashMap<String, String> headers, Response.Listener successHandler,
                                Response.ErrorListener errorHandler) {
            url = BASIC_URL + url;
            JsonObjectRequest request = new JsonObjectRequest(httpMethod, url, parameters, successHandler, errorHandler);
            request.setRetryPolicy(
                    new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );
            RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);
        }


}
