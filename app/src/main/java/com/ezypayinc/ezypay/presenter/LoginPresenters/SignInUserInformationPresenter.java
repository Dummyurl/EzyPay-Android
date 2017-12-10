package com.ezypayinc.ezypay.presenter.LoginPresenters;

import android.app.ProgressDialog;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInUserInformationView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

public class SignInUserInformationPresenter implements ISignInUserInformationPresenter {

    private SignInUserInformationView view;

    public SignInUserInformationPresenter(SignInUserInformationView view) {
        this.view = view;
    }

    public void validateUserEmail(final User user) {
        view.showProgressDialog();
        UserManager manager = new UserManager();
        try {
            manager.validateUserEmail(user.getEmail(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    JsonObject object = response.getAsJsonObject();
                    int isAlreadySaved =  object.get("user").getAsInt();
                    view.hideProgressDialog();
                    if(isAlreadySaved == 0) {
                        addUser(user);
                    } else {
                        view.userIsAlreadySaved();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            view.hideProgressDialog();
        }
    }

    @Override
    public void registerUser(String username, String lastName, String phoneNumber, String email, String password, String code) {
        if(validateFields(username, lastName, phoneNumber, email, password, code)) {
            final User user = new User();
            user.setName(username);
            user.setLastName(lastName);
            user.setPhoneNumber(code + phoneNumber);
            user.setEmail(email);
            user.setPassword(password);
            validateUserEmail(user);
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    private boolean validateFields(String username, String lastName, String phoneNumber, String email, String password, String code) {
        if (TextUtils.isEmpty(username)) {
            view.setErrorMessage(R.id.sign_in_name, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(lastName)) {
            view.setErrorMessage(R.id.sign_in_lastname, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(phoneNumber)) {
            view.setErrorMessage(R.id.sign_in_phone_number, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(email)) {
            view.setErrorMessage(R.id.sign_in_email, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(code)) {
            view.setErrorMessage(R.id.sign_in_code_phone_number, R.string.error_field_required);
            return false;
        }

        if(!validateEmail(email)){
            view.setErrorMessage(R.id.sign_in_email, R.string.error_field_required);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            view.setErrorMessage(R.id.sign_in_password, R.string.error_field_required);
            return false;
        }

        if (password.length() < 4) {
            view.setErrorMessage(R.id.sign_in_password, R.string.error_invalid_password);
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
        view.navigateToPaymentView();
    }
}
