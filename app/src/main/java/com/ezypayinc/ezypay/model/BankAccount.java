package com.ezypayinc.ezypay.model;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class BankAccount {

    private String accountNumber;
    private String bank;
    private String userAccount;
    private String userIdentification;

    public BankAccount() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserIdentification() {
        return userIdentification;
    }

    public void setUserIdentification(String userIdentification) {
        this.userIdentification = userIdentification;
    }

    public JSONObject toJSON() throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return new JSONObject(json);
    }
}
