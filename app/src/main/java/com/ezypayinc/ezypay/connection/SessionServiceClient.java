package com.ezypayinc.ezypay.connection;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gustavoquesada on 11/21/16.
 */

public class SessionServiceClient {
    private ConnectionManager connectionManager;

    private static final String CLIENT_ID  = "ceWZ_4G8CjQZy7,8";
    private static final String SECRET_KEY = "9F=_wPs^;W]=Hqf!3e^)ZpdR;MUym+";
    private static final String CONTENT_TYPE = "application/json";

    public SessionServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void loginMethod(String username, String password, Response.Listener successHandler,
                              Response.ErrorListener failureHandler) throws JSONException {
        final String basicAuth = "Basic " + Base64.encodeToString((CLIENT_ID + ":"+ SECRET_KEY).getBytes(), Base64.NO_WRAP);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("grant_type","password");
        parameters.put("username", username);
        parameters.put("password", password);

        connectionManager.sendRequest(Request.Method.POST, "auth/token", parameters, headers, successHandler, failureHandler);
    }

    public User parseLoginMethod(JSONObject response) throws JSONException {
        JSONObject object = response.getJSONObject("access_token");
        User user = new User();
        user.setId(object.getInt("userId"));
        user.setToken(object.getString("value"));
        return user;
    }


}
