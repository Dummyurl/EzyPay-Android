package com.ezypayinc.ezypay.connection;


import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.Currency;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CurrencyServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "currency/";
    private static final String CONTENT_TYPE = "application/json";

    public CurrencyServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void getAllCurrencies(String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String oauthToken = "Bearer "+ token;
        String url = BASE_URL + "getAll";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);

        connectionManager.sendCustomRequest(Request.Method.POST, url, null, headers, successHandler, failureHandler);
    }

    public List<Currency> parseCurrencyList(JsonElement response) {
        Gson gson = new Gson();
        Currency[] currencyArray = gson.fromJson(response, Currency[].class);
        List<Currency> currencyList = Arrays.asList(currencyArray);
        return currencyList;
    }
}