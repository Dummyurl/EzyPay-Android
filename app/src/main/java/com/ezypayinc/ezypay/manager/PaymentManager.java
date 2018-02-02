package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.PaymentServiceClient;
import com.ezypayinc.ezypay.database.PaymentData;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;


public class PaymentManager {

    /**
     *
     * Database Methods
     *
     * **/
    public void addPayment(Payment payment) {
        PaymentData data = new PaymentData();
        data.addPayment(payment);
    }

    public void deletePayment() {
        PaymentData data = new PaymentData();
        data.deletePayment();
    }

    public void removeFriendsFromPayment(Payment payment) {
        PaymentData data = new PaymentData();
        data.removeFriendsFromPayment(payment);
    }

    public void updatePayment(Payment payment) {
        PaymentData data = new PaymentData();
        data.updatePayment(payment);
    }


    public Payment getPayment() {
        PaymentData data = new PaymentData();
        return data.getPayment();
    }

    public Payment updatePaymentDate(Payment payment, String paymentDate) {
        PaymentData data = new PaymentData();
        return data.updatePaymentDate(payment, paymentDate);
    }

    /**
     *
     * WEB Service Methods
     *
     * **/
    public void getActivePaymentByUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        PaymentServiceClient service = new PaymentServiceClient();
        service.getActivePaymentByUser(user, successHandler, failureHandler);
    }


    public Payment parsePayment(JsonElement response) {
        PaymentServiceClient service = new PaymentServiceClient();
        return  service.parsePayment(response);
    }

    public void getPaymentById(int paymentId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        PaymentServiceClient service = new PaymentServiceClient();
        service.getPaymentById(paymentId, token, successHandler, failureHandler);
    }

    public void createPayment(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.createPayment(payment, token, successHandler, failureHandler);
    }

    public void deletePayment(int paymentId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        PaymentServiceClient service = new PaymentServiceClient();
        service.deletePayment(paymentId, token, successHandler, failureHandler);
    }

    public void updatePayment(Payment payment, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.updatePayment(payment, user, successHandler, failureHandler);
    }

    public void addPaymentToFriends(Payment payment, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.addPaymentToFriends(payment, user,successHandler, failureHandler);
    }

    public void performPayment(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.performPayment(payment, token, successHandler, failureHandler);
    }

    public void updateUserPayment(User user, int paymentId, int state, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.updateUserPayment(user, paymentId, state, successHandler, failureHandler);
    }

    public void updatePaymentAmount(int paymentId, int currencyId, float amount, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        PaymentServiceClient service = new PaymentServiceClient();
        service.updatePaymentAmount(paymentId, currencyId, amount, token, successHandler, failureHandler);
    }
}
