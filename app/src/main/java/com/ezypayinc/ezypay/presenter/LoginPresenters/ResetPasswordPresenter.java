package com.ezypayinc.ezypay.presenter.LoginPresenters;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.IResetPasswordView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class ResetPasswordPresenter implements IResetPasswordPresenter {

    private IResetPasswordView mView;

    public ResetPasswordPresenter(IResetPasswordView view) {
        mView = view;
    }

    @Override
    public void resetPassword(String email) {
        mView.showProgressDialog();
        UserManager userManager = new UserManager();
        try {
            userManager.forgotPassword(email, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    mView.hideProgressDialog();
                    mView.goToLoginActivity();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.hideProgressDialog();
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            mView.hideProgressDialog();
        }
    }
}
