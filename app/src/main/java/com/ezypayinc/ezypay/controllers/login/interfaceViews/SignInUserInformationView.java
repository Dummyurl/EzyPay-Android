package com.ezypayinc.ezypay.controllers.login.interfaceViews;

public interface SignInUserInformationView {

    void showProgressDialog();
    void hideProgressDialog();
    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void userIsAlreadySaved();
    void navigateToPaymentView();
}
