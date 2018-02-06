package com.ezypayinc.ezypay.controllers.login.interfaceViews;

public interface IResetPasswordView {
    void showProgressDialog();
    void hideProgressDialog();
    void goToLoginActivity();
    void onNetworkError(Object error);
}
