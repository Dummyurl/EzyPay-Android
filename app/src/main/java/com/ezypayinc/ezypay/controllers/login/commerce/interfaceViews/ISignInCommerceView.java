package com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews;

public interface ISignInCommerceView {
    void showProgressDialog();
    void dismissProgressDialog();
    void goToNextView();
    void userIsAlreadySaved();
    void onNetworkError(Object error);
    void nameRequiredError();
    void phoneRequiredError();
    void emailRequiredError();
    void passwordRequiredError();
    void emailFormatError();
    void passwordFormatError();
}
