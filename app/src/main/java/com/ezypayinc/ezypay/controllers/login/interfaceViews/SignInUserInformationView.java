package com.ezypayinc.ezypay.controllers.login.interfaceViews;

public interface SignInUserInformationView {

    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void navigateToPaymentView();
}
