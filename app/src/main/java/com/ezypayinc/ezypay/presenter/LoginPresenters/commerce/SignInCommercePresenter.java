package com.ezypayinc.ezypay.presenter.LoginPresenters.commerce;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInCommerceView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;


public class SignInCommercePresenter implements ISignInCommercePresenter {

    private ISignInCommerceView mView;

    public SignInCommercePresenter(ISignInCommerceView view) {
        mView = view;
    }

    @Override
    public void registerCommerce(User user, String code) {
        if(validateFields(user, code)) {

        }
    }

    public void validateUserEmail(final User user) {
        mView.showProgressDialog();
        UserManager manager = new UserManager();
        try {
            manager.validateUserEmail(user.getEmail(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    JsonObject object = response.getAsJsonObject();
                    int isAlreadySaved =  object.get("user").getAsInt();
                    mView.dismissProgressDialog();
                    if(isAlreadySaved == 0) {
                        addUser(user);
                    } else {
                        mView.userIsAlreadySaved();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.dismissProgressDialog();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            mView.dismissProgressDialog();
        }
    }

    private boolean validateFields(User user, String code) {
        if (TextUtils.isEmpty(user.getName())) {
           mView.nameRequiredError();
            return false;
        }

        if(TextUtils.isEmpty(user.getPhoneNumber())) {
            mView.phoneRequiredError();
            return false;
        }

        if(TextUtils.isEmpty(user.getEmail())) {
            mView.emailRequiredError();
            return false;
        }

        if(TextUtils.isEmpty(code)) {
            //view.setErrorMessage(R.id.sign_in_code_phone_number, R.string.error_field_required);
            return false;
        }

        if(!validateEmail(user.getEmail())){
            mView.emailFormatError();
            return false;
        }

        if (TextUtils.isEmpty(user.getPassword())) {
            mView.passwordRequiredError();
            return false;
        }

        if (user.getPassword().length() < 4) {
            mView.passwordFormatError();
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    private void addUser(final User user) {
        UserManager manager = new UserManager();
        UserSingleton.getInstance().setUser(user);
        manager.deleteUser();
        manager.addUser(user);
        mView.goToNextView();
    }
}
