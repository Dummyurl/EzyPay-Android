package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.PushNotificationsServiceClient;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
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

    public void sendBillNotification(int clientId, String currencyCode, float amount,
                                     int paymentId, String token,
                                     Response.Listener<JsonElement> successHandler,
                                     Response.ErrorListener failureHandler) throws JSONException {
        PushNotificationsServiceClient service = new PushNotificationsServiceClient();
        service.sendBillNotification(clientId, currencyCode, amount, paymentId, token, successHandler, failureHandler);
    }

    public void sendPaymentNotifications(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PushNotificationsServiceClient service = new PushNotificationsServiceClient();
        service.sendPaymentNotifications(payment, token, successHandler, failureHandler);
    }

    public void responseSplitNotification(User user, int paymentId, int response, int clientId, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PushNotificationsServiceClient service = new PushNotificationsServiceClient();
        service.responseSplitNotification(user, paymentId, response, clientId, successHandler, failureHandler);
    }
}
