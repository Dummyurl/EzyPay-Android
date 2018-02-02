package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Currency;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentServiceClient {

    private ConnectionManager connectionManager;
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASIC_URL = "payment/";
    private static final String USER_PAYMENT_URL = "userPayment/";

    public PaymentServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void getActivePaymentByUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = BASIC_URL + "activePayment/" + String.valueOf(user.getId());
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);
    }

    public Payment parsePayment(JsonElement response) {
        if(response.isJsonObject()) {
            JsonObject jsonObject = response.getAsJsonObject();
            Payment payment = new Payment();
            payment.setId(jsonObject.get("id").isJsonNull() ? 0 : jsonObject.get("id").getAsInt());
            payment.setUserId(jsonObject.has("userId")?(jsonObject.get("userId").isJsonNull() ? 0 : jsonObject.get("userId").getAsInt()) : 0);
            payment.setCost(jsonObject.get("cost").isJsonNull() ? 0 : jsonObject.get("cost").getAsFloat());
            payment.setUserCost(jsonObject.has("userCost")? (jsonObject.get("userCost").isJsonNull() ? 0 : jsonObject.get("userCost").getAsFloat()) : 0);
            payment.setEmployeeId(jsonObject.get("employeeId").isJsonNull() ? 0 : jsonObject.get("employeeId").getAsInt());
            payment.setCanceled(!(jsonObject.get("isCanceled").isJsonNull() ? false : !jsonObject.get("isCanceled").getAsBoolean()));
            payment.setTableNumber(jsonObject.get("tableNumber") == null ? 0 : jsonObject.get("tableNumber").getAsInt());
            payment.setCommerce(jsonObject.has("Commerce") ? getCommerce(jsonObject.get("Commerce").getAsJsonObject()) : null);
            if(jsonObject.has("Currency") && jsonObject.get("Currency") != null) { payment.setCurrency(jsonObject.get("Currency").isJsonNull() ? null : getCurrency(jsonObject.get("Currency").getAsJsonObject())); }
            List<Friend> friends = getFriends(!jsonObject.has("Friends") ? null :
                    (jsonObject.get("Friends").isJsonNull() ? null : jsonObject.get("Friends").getAsJsonArray()));
            if(friends != null) {
                payment.getFriends().addAll(friends);
            }

            return payment;
        }
        return null;
    }

    public void getPaymentById(int paymentId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = BASIC_URL + String.valueOf(paymentId);
        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);
    }

    private User getCommerce(JsonObject object) {
        if(!object.isJsonNull()) {
            User commerce = new User();
            commerce.setId(object.get("id").isJsonNull() ? 0 : object.get("id").getAsInt());
            commerce.setName(object.get("name").isJsonNull() ? null : object.get("name").getAsString());
            commerce.setUserType(object.get("userType").isJsonNull() ? 0 : object.get("userType").getAsInt());
            commerce.setAvatar(object.get("avatar").isJsonNull()? null : object.get("avatar").getAsString());
            return commerce;
        }
        return null;
    }

    private Currency getCurrency(JsonObject object)
    {
        if(!object.isJsonNull() && object.has("id")) {
            Currency currency = new Currency();
            currency.setId(object.get("id").isJsonNull() ? 0 : object.get("id").getAsInt());
            currency.setName(object.get("name").isJsonNull() ? null : object.get("name").getAsString());
            currency.setCode(object.get("code").isJsonNull() ? null : object.get("code").getAsString());
            return currency;
        }
        return null;
    }

    private List<Friend> getFriends(JsonArray friendsArray) {
        if(friendsArray != null && !friendsArray.isJsonNull()) {
            List<Friend> friends = new ArrayList<>();
            for (int i = 0; i < friendsArray.size() ; i++) {
                JsonObject jsonPayment = friendsArray.get(i).getAsJsonObject();
                Friend friend = new Friend();
                friend.setId(jsonPayment.get("id").isJsonNull() ? 0 : jsonPayment.get("id").getAsInt());
                friend.setCost(jsonPayment.get("cost").isJsonNull() ? 0 : jsonPayment.get("cost").getAsFloat());
                friend.setAvatar(jsonPayment.get("avatar").isJsonNull() ? null : jsonPayment.get("avatar").getAsString());
                friend.setName(jsonPayment.get("name").isJsonNull() ? null : jsonPayment.get("name").getAsString());
                friend.setLastname(jsonPayment.get("lastname").isJsonNull() ? null : jsonPayment.get("lastname").getAsString());
                friend.setState(jsonPayment.get("state").isJsonNull() ? 0 : jsonPayment.get("state").getAsInt());

                friends.add(friend);
            }
            return friends;
        }
        return null;
    }

    public void createPayment(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        JSONObject object = payment.toJSON();
        object.put("commerceId", payment.getCommerce().getId());
        object.put("currencyId", payment.getCurrency() == null ? 1 : payment.getCurrency().getId());

        connectionManager.sendCustomRequest(Request.Method.POST, BASIC_URL, object, headers, successHandler, failureHandler);
    }

    public void deletePayment(int paymentId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = BASIC_URL  + String.valueOf(paymentId);
        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.DELETE, url, null, headers, successHandler, failureHandler);
    }

    public void updatePayment(Payment payment, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL  + String.valueOf(payment.getId());
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        JSONObject object = new JSONObject();
        object.put("commerceId", payment.getCommerce().getId());
        object.put("userId", user.getId());
        object.put("cost", payment.getCost());
        object.put("paymentDate", payment.getPaymentDate());

        connectionManager.sendCustomRequest(Request.Method.PUT, url, object, headers, successHandler, failureHandler);
    }

    public void addPaymentToFriends(Payment payment, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = USER_PAYMENT_URL + "addFriends";
        String oauthToken = "Bearer "+ user.getToken();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("friends", getArrayFriends(payment, user));

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    private JSONArray getArrayFriends(Payment payment, User user) throws JSONException {
        JSONArray friendsArray = new JSONArray();
        friendsArray.put(friendData(user.getId(), payment.getId(), payment.getUserCost(), 1));
        for (Friend friend: payment.getFriends()) {
            friendsArray.put(friendData(friend.getId(), payment.getId(), friend.getCost(),0));
        }
        return friendsArray;
    }

    private JSONObject friendData(int userId, int paymentId, float cost, int state) throws JSONException {
        JSONObject userToFriend = new JSONObject();
        userToFriend.put("userId", userId);
        userToFriend.put("paymentId", paymentId);
        userToFriend.put("cost", cost);
        userToFriend.put("state", state);

        return userToFriend;
    }

    public void performPayment(Payment payment, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "pay";
        String oauthToken = "Bearer "+ token;

        JSONObject parameters = new JSONObject();
        parameters.put("id", payment.getId());

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public void updateUserPayment(User user, int paymentId, int state, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = USER_PAYMENT_URL + String.valueOf(paymentId);
        JSONObject parameters = friendData(user.getId(), paymentId, 0, state);
        parameters.remove("cost");

        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.PUT, url, parameters, headers, successHandler, failureHandler);
    }

    public void updatePaymentAmount(int paymentId, int currencyId, float amount, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + String.valueOf(paymentId);
        JSONObject parameters = new JSONObject();
        parameters.put("currencyId", currencyId);
        parameters.put("cost", amount);

        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ token;
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.PUT, url, parameters, headers, successHandler, failureHandler);
    }
}
