package com.ezypayinc.ezypay.connection;

import android.util.Base64;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.model.UserHistory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserServiceClient {
    private ConnectionManager connectionManager;
    private static final String CLIENT_ID  = "ceWZ_4G8CjQZy7,8";
    private static final String SECRET_KEY = "9F=_wPs^;W]=Hqf!3e^)ZpdR;MUym+";
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASIC_URL = "user/";
    private static final String HISTORY_URL = "history/";
    private static final String HISTORY_DATES_URL = "history/dates/";
    private static final String COUNTRY_URL = "country/getAll/";

    public UserServiceClient() {
        connectionManager = new ConnectionManager();
    }

    /*register user*/
    public void registerUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        final String basicAuth = "Basic " + Base64.encodeToString((CLIENT_ID + ":"+ SECRET_KEY).getBytes(), Base64.NO_WRAP);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("name", user.getName());
        parameters.put("lastName", user.getLastName());
        parameters.put("phoneNumber", user.getPhoneNumber());
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());
        parameters.put("userType", user.getUserType());

        int httpMethod = Request.Method.POST;
        connectionManager.sendCustomRequest(httpMethod, BASIC_URL, parameters, headers, successHandler, failureHandler);
    }

    public User parseRegisterUser(JsonElement response, User user) {
        int userId = response.getAsJsonObject().get("id").getAsInt();
        int customerId = response.getAsJsonObject().get("customerId").getAsInt();
        user.setId(userId);
        user.setCustomerId(customerId);
        return user;
    }


    /*get user by id*/
    public void getUserById(int userId, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        String url = BASIC_URL + String.valueOf(userId);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);

    }

    public User parseUserFromServer(JsonElement response) {
        User user = new User();
        //get data
        JsonObject object = response.getAsJsonObject();
        int id = object.get("id").getAsInt();
        String email = object.get("email").getAsString();
        String name = object.get("name").getAsString();
        String lastName = object.get("lastName").getAsString();
        String phoneNumber = object.get("phoneNumber").getAsString();
        String avatar =  object.get("avatar").getAsString();
        int userType = object.get("userType").getAsInt();
        int customerId = object.get("customerId").getAsInt();

        //set user
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setAvatar(avatar);
        user.setUserType(userType);
        user.setCustomerId(customerId);

        return user;
    }

    public void validatePhoneNumbers(JSONArray phoneNumbers,Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        JSONObject parameters = new JSONObject();
        parameters.put("phoneNumbers", phoneNumbers);
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        String url = BASIC_URL + "validatePhoneNumbers";

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public List<Friend> parseValidatePhoneNumbers(JsonElement jsonElement) {
        List<Friend> friends = new ArrayList<>();
        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject object = jsonArray.get(i).getAsJsonObject();
                Friend friend = new Friend();
                friend.setId(object.get("id").getAsInt());
                friend.setName(object.get("name").getAsString());
                friend.setLastname(object.get("lastName").getAsString());
                friend.setAvatar(object.get("avatar").getAsString());
                friends.add(friend);
            }
        }
        return friends;
    }

    /*update user*/
    public void updateUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        User userToUpdate = UserSingleton.getInstance().getUser();
        JSONObject parameters = new JSONObject();
        parameters.put("name", user.getName());
        parameters.put("lastName", user.getLastName());
        parameters.put("phoneNumber", user.getPhoneNumber());
        parameters.put("email", user.getEmail());

        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ userToUpdate.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        String url = BASIC_URL + String.valueOf(userToUpdate.getId());

        connectionManager.sendCustomRequest(Request.Method.PUT, url, parameters, headers, successHandler, failureHandler);
    }

    public void getUserHistory(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = BASIC_URL + HISTORY_URL + String.valueOf(user.getId());

        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);
    }

    public List<UserHistory> parseUserHistory(JsonElement response) {
        Gson gson = new Gson();
        UserHistory[] historyArray = gson.fromJson(response,UserHistory[].class);
        List<UserHistory> userHistoryList = Arrays.asList(historyArray);
        return userHistoryList;
    }

    public void getUserHistoryDates(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = BASIC_URL + HISTORY_DATES_URL + String.valueOf(user.getId());

        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);
    }

    public List<HistoryDate> parseUserHistoryDates(JsonElement response) {
        Gson gson = new Gson();
        HistoryDate[] historyArray = gson.fromJson(response,HistoryDate[].class);
        List<HistoryDate> dateList = Arrays.asList(historyArray);
        return dateList;
    }

    public void updatePassword(String newPassword, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "password";
        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("newPassword", newPassword);
        parameters.put("email", user.getEmail());

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public void uploadImge(byte[] encodeImage, User user, Response.Listener<NetworkResponse> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "uploadImage/" + String.valueOf(user.getId());
        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);

        connectionManager.sendMultiPartRequest(Request.Method.POST, url,encodeImage, headers, successHandler, failureHandler);
    }

    public void getPhoneCodes(Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String url = COUNTRY_URL;
        HashMap<String, String> headers = new HashMap<>();
        final String basicAuth = "Basic " + Base64.encodeToString((CLIENT_ID + ":"+ SECRET_KEY).getBytes(), Base64.NO_WRAP);
        headers.put("Authorization", basicAuth);
        headers.put("Content-Type", CONTENT_TYPE);
        connectionManager.sendCustomRequest(Request.Method.POST, url, null, headers, successHandler, failureHandler);
    }

    public List<PhoneCode> parsePhoneCode(JsonElement response) {
        Gson gson = new Gson();
        PhoneCode[] phoneCodeArray = gson.fromJson(response, PhoneCode[].class);
        List<PhoneCode> phoneCodeList = Arrays.asList(phoneCodeArray);
        return phoneCodeList;
    }

    public void  validateUserEmail(String email, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASIC_URL + "email";
        final String basicAuth = "Basic " + Base64.encodeToString((CLIENT_ID + ":"+ SECRET_KEY).getBytes(), Base64.NO_WRAP);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", basicAuth);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("email", email);
        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

}
