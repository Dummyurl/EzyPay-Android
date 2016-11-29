package com.ezypayinc.ezypay.controllers.login.interfaceViews;

/**
 * Created by gustavoquesada on 11/23/16.
 */

public interface LoginView {

    void setUsernameError(int error);

    void setPasswordError(int error);

    void onNetworkError(Object error);

    void showProgressDialog();

    void hideProgressDialog();

    void navigateToHome();
}
