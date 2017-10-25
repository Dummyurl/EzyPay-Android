package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.PushNotificationsServiceClient;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class PushNotificationsManager {

    public void callWaiterNotification(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PushNotificationsServiceClient service = new PushNotificationsServiceClient();
        service.callWaiterNotification(payment, token, successHandler, failureHandler);
    }

    public void sendBillRequestNotification(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PushNotificationsServiceClient service = new PushNotificationsServiceClient();
        service.sendBillRequestNotification(payment, token, successHandler, failureHandler);
    }
}
