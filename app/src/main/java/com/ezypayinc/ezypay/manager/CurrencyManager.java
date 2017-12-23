package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.CurrencyServiceClient;
import com.ezypayinc.ezypay.model.Currency;
import com.google.gson.JsonElement;

import java.util.List;

public class CurrencyManager {

    public void getAllCurrencies(String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        CurrencyServiceClient serviceClient = new CurrencyServiceClient();
        serviceClient.getAllCurrencies(token, successHandler, failureHandler);
    }

    public List<Currency> parseCurrencyList(JsonElement response) {
        CurrencyServiceClient serviceClient = new CurrencyServiceClient();
        return serviceClient.parseCurrencyList(response);
    }
}
