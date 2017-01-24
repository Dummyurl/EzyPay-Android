package com.ezypayinc.ezypay.controllers.login.interfaceViews;

public interface LoginView {

    void setUsernameError(int error);

    void setPasswordError(int error);

    void onNetworkError(Object error);

    void showProgressDialog();

    void hideProgressDialog();

    void navigateToHome();
}
