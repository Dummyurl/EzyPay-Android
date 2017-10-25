package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gustavoquesada on 10/24/17.
 */

public class PushNotificationsServiceClient {

    private final ConnectionManager connectionManager;
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASIC_URL = "notifications/";

    public PushNotificationsServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void callWaiterNotification(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "callWaiter/";
        int commerceId = payment.getEmployeeId() == 0 ? payment.getCommerce().getId() : payment.getEmployeeId();
        JSONObject parameters = new JSONObject();
        parameters.put("tableNumber", payment.getTableNumber());
        parameters.put("commerceId", commerceId);
        parameters.put("paymentId", payment.getId());

        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public void sendBillRequestNotification(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "billRequest/";
        int commerceId = payment.getEmployeeId() == 0 ? payment.getCommerce().getId() : payment.getEmployeeId();
        JSONObject parameters = new JSONObject();
        parameters.put("tableNumber", payment.getTableNumber());
        parameters.put("commerceId", commerceId);
        parameters.put("paymentId", payment.getId());

        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);

    }
}
