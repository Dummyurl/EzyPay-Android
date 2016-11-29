package com.ezypayinc.ezypay.connection;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ezypayinc.ezypay.base.EzyPayApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustavoquesada on 11/4/16.
 */

public class ConnectionManager {

    private static final String BASIC_URL = "http://192.168.1.102:3000/";
    private Context mContext;

    public ConnectionManager() {
        mContext = EzyPayApplication.getInstance();
    }


        public void sendRequest(int httpMethod, String url, JSONObject parameters, final HashMap<String, String> headers, Response.Listener successHandler,
                                Response.ErrorListener errorHandler) {
            url = BASIC_URL + url;
            JsonObjectRequest request = new JsonObjectRequest(httpMethod, url, parameters, successHandler, errorHandler){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers == null? super.getHeaders() : headers;
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
