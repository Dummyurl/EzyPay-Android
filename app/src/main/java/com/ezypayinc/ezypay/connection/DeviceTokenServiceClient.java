package com.ezypayinc.ezypay.connection;


import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.LocalToken;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DeviceTokenServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASIC_URL  = "deviceToken/";
    private static final String CONTENT_TYPE = "application/json";


    public DeviceTokenServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void registerDeviceToken(LocalToken localToken, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject object = localToken.toJSON();
        connectionManager.sendCustomRequest(Request.Method.POST, BASIC_URL, object, headers, successHandler, failureHandler);
    }
}
