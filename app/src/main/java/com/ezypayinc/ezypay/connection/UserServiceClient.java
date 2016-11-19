package com.ezypayinc.ezypay.connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustavoquesada on 11/4/16.
 */

public class UserServiceClient {
    private ConnectionManager connectionManager;

    public UserServiceClient(Context context) {
        connectionManager = new ConnectionManager(context);
    }

    /*Session method*/
    public void login(String email, String password, Response.Listener successHandler,
                      Response.ErrorListener failureHandler) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("email", email);
        parameters.put("password", password);

        int httpMethod = Request.Method.POST;
        String url = "login";
        connectionManager.sendRequest(httpMethod, url, parameters, null, successHandler,failureHandler);
    }

    /*register user*/
    public void registerUser(User user, Response.Listener successHandler, Response.ErrorListener failureHandler) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("name", user.getName());
        parameters.put("lastname", user.getLastname());
        parameters.put("phoneNumber", user.getPhoneNumber());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());

        String url = "user/create";
        int httpMethod = Request.Method.POST;
        connectionManager.sendRequest(httpMethod, url, parameters, null, successHandler, failureHandler);
    }
}
