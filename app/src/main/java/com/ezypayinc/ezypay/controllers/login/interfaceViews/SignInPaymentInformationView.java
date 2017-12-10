package com.ezypayinc.ezypay.controllers.login.interfaceViews;

import java.util.ArrayList;

public interface SignInPaymentInformationView {

    void showProgressDialog();
    void hideProgressDialog();
    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void navigateToHome();
}
