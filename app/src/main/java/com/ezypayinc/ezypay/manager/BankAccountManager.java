package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.BankAccountServiceClient;
import com.ezypayinc.ezypay.model.BankAccount;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class BankAccountManager {

    public void getBankAccountByUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        BankAccountServiceClient service = new BankAccountServiceClient();
        service.getBankAccountByUser(user, successHandler, failureHandler);
    }

    public BankAccount parseBankAccount(JsonElement response) {
        BankAccountServiceClient service = new BankAccountServiceClient();
        return service.parseBankAccount(response);
    }

    public void registerAccount(BankAccount bankAccount, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        BankAccountServiceClient service = new BankAccountServiceClient();
        service.registerAccount(bankAccount, user, successHandler, failureHandler);
    }

    public void updateAccount(BankAccount bankAccount, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        BankAccountServiceClient service = new BankAccountServiceClient();
        service.updateAccount(bankAccount, user, successHandler, failureHandler);
    }
}
