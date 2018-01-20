package com.ezypayinc.ezypay.connection;

import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SessionServiceClient {
    private ConnectionManager connectionManager;

    private static final String CLIENT_ID  = "ceWZ_4G8CjQZy7,8";
    private static final String SECRET_KEY = "9F=_wPs^;W]=Hqf!3e^)ZpdR;MUym+";
    private static final String CONTENT_TYPE = "application/json";

    public SessionServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void loginMethod(String username, String password, String scope, String platformToken,
                            Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        final String basicAuth = "Basic " + Base64.encodeToString((CLIENT_ID + ":"+ SECRET_KEY).getBytes(), Base64.NO_WRAP);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        if(scope != null) {
            parameters.put("grant_type", "password");
            parameters.put("username", username);
            parameters.put("password", password);
            parameters.put("scope", scope);
            headers.put("PlatformToken", platformToken);
        } else {
            parameters.put("grant_type", "password");
            parameters.put("username", username);
            parameters.put("password", password);
        }

        connectionManager.sendCustomRequest(Request.Method.POST, "auth/token", parameters, headers, successHandler, failureHandler);
    }

    public User parseLoginMethod(JsonElement response) {
        JsonObject object = response.getAsJsonObject().get("access_token").getAsJsonObject();
        User user = new User();
        user.setId(object.get("userId").getAsInt());
        user.setToken(object.get("value").getAsString());
        return user;
    }

    public User parseFacebookLogin(JsonElement response) {
        JsonObject object = response.getAsJsonObject();
        User user = new User();
        if(object.has("userId")) {
            user.setId(object.get("userId").getAsInt());
            user.setToken(object.get("value").getAsString());
            return user;
        }
        return null;
    }


}
