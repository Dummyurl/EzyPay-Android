package com.ezypayinc.ezypay.controllers.login.interfaceViews;

/**
 * Created by gustavoquesada on 11/24/16.
 */

public interface SignInUserInformationView {

    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void navigateToPaymentView();
}
