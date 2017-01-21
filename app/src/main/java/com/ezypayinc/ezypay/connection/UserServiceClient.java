package com.ezypayinc.ezypay.connection;

import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
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

/**
 * Created by gustavoquesada on 11/4/16.
 */

public class UserServiceClient {
    private ConnectionManager connectionManager;
    private static final String CLIENT_ID  = "ceWZ_4G8CjQZy7,8";
    private static final String SECRET_KEY = "9F=_wPs^;W]=Hqf!3e^)ZpdR;MUym+";
    private static final String CONTENT_TYPE = "application/json";
    private static final String BASIC_URL = "user/";

    public UserServiceClient() {
        connectionManager = new ConnectionManager();
    }

    /*register user*/
    public void registerUser(User user, Response.Listener successHandler, Response.ErrorListener failureHandler) throws JSONException {
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

        int httpMethod = Request.Method.POST;
        connectionManager.sendRequest(httpMethod, BASIC_URL, parameters, headers, successHandler, failureHandler);
    }

    public int parseRegisterUser(JSONObject response) throws JSONException {
        return  response.getInt("userId");
    }


    /*get user by id*/
    public void getUserById(int userId, Response.Listener successHandler, Response.ErrorListener failureHandler) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        String url = BASIC_URL + String.valueOf(userId) ;

        connectionManager.sendRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);

    }

    public User parseUserFromServer(JSONObject response) {
        User user = new User();
        try {
            int id = response.getInt("id");
            String email = response.getString("email");
            String name = response.getString("name");
            String lastName = response.getString("lastName");
            String phoneNumber = response.getString("phoneNumber");

            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    public void validatePhoneNumbers(JSONArray phoneNumbers,Response.Listener successHandler, Response.ErrorListener failureHandler) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        phoneNumbers = new JSONArray();
        phoneNumbers.put("89638295");
        phoneNumbers.put("88645200");
        phoneNumbers.put("87921284");
        JSONObject parameters = new JSONObject();
        parameters.put("phoneNumbers", phoneNumbers);
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        String url = BASIC_URL + "validatePhoneNumbers";

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public List<User> parseValidatePhoneNumbers(JsonElement jsonElement) {
        List<User> userList = new ArrayList<>();
        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject object = jsonArray.get(i).getAsJsonObject();
                User user = new User();
                user.setId(object.get("id").getAsInt());
                user.setEmail(object.get("email").getAsString());
                user.setName(object.get("name").getAsString());
                user.setLastName(object.get("lastName").getAsString());
                user.setPhoneNumber(object.get("phoneNumber").getAsString());
                userList.add(user);
            }
        }
        return userList;
    }

}
