package com.ezypayinc.ezypay.presenter.CommerceSettingsPresenters;


import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.bankAccount.interfaceViews.IBankAccountView;
import com.ezypayinc.ezypay.manager.BankAccountManager;
import com.ezypayinc.ezypay.model.BankAccount;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class BankAccountPresenter implements IBankAccountPresenter {

    private IBankAccountView mView;

    public BankAccountPresenter(IBankAccountView view) {
        mView = view;
    }

    @Override
    public void getBankAccount() {
        User user = UserSingleton.getInstance().getUser();
        if(user == null) { return; }
        mView.showProgressDialog();
        final BankAccountManager manager = new BankAccountManager();
        try {
            manager.getBankAccountByUser(user, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    BankAccount bankAccount = manager.parseBankAccount(response);
                    mView.populateAccount(bankAccount);
                    mView.dismissProgressDialog();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.dismissProgressDialog();
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            mView.dismissProgressDialog();
        }
    }

    @Override
    public void insertBankAccount(BankAccount bankAccount) {
        if(validateFields(bankAccount)) {
            mView.showProgressDialog();
            User currentUser = UserSingleton.getInstance().getUser();
            BankAccountManager manager = new BankAccountManager();
            try {
                manager.registerAccount(bankAccount, currentUser, new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        mView.dismissProgressDialog();
                        mView.goToSettingsView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.dismissProgressDialog();
                        mView.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                mView.dismissProgressDialog();
            }
        }

    }

    @Override
    public void updateBankAccount(BankAccount bankAccount) {
        if(validateFields(bankAccount)) {
            mView.showProgressDialog();
            User currentUser = UserSingleton.getInstance().getUser();
            BankAccountManager manager = new BankAccountManager();
            try {
                manager.updateAccount(bankAccount, currentUser, new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        mView.dismissProgressDialog();
                        mView.goToSettingsView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.dismissProgressDialog();
                        mView.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                mView.dismissProgressDialog();
            }
        }
    }

    private boolean validateFields(BankAccount bankAccount) {
        if(bankAccount != null) {
            if (TextUtils.isEmpty(bankAccount.getUserIdentification())) {
                mView.commerceIdRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getAccountNumber())) {
                mView.accountNumberRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getUserAccount())) {
                mView.accountNameRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getBank())) {
                mView.bankRequiredError();
                return false;
            }
            return true;
        }
        return false;
    }


}
