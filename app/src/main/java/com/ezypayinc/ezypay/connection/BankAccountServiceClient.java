package com.ezypayinc.ezypay.connection;


import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.BankAccount;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BankAccountServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "bankAccount/";
    private static final String CONTENT_TYPE = "application/json";

    public BankAccountServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void getBankAccountByUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASE_URL + "getAll";
        HashMap<String, String> headers = new HashMap<>();
        String oauthToken = "Bearer "+ user.getToken();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        JSONObject parameters = new JSONObject();
        parameters.put("userId", user.getId());

        connectionManager.sendCustomRequest(Request.Method.POST, url, parameters, headers, successHandler, failureHandler);
    }

    public BankAccount parseBankAccount(JsonElement response) {
        Gson gson = new Gson();
        BankAccount[] bankAccountArray = gson.fromJson(response,BankAccount[].class);
        List<BankAccount> bankAccountList = Arrays.asList(bankAccountArray);
        if(bankAccountList != null && bankAccountList.size() > 0) {
            return bankAccountList.get(0);
        }
        return null;
    }

    public void registerAccount(BankAccount bankAccount, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = bankAccount.toJSON();
        parameters.put("userId", user.getId());

        int httpMethod = Request.Method.POST;
        connectionManager.sendCustomRequest(httpMethod,BASE_URL,parameters,headers,successHandler, failureHandler);
    }

    public void updateAccount(BankAccount bankAccount, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        String url = BASE_URL + String.valueOf(user.getId());
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = bankAccount.toJSON();
        parameters.put("userId", user.getId());

        int httpMethod = Request.Method.PUT;
        connectionManager.sendCustomRequest(httpMethod,url,parameters,headers,successHandler, failureHandler);
    }
}
