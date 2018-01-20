package com.ezypayinc.ezypay.controllers.login.interfaceViews;

import com.ezypayinc.ezypay.model.User;

public interface SignInUserInformationView {

    void setupView(User user);
    void showProgressDialog();
    void hideProgressDialog();
    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void userIsAlreadySaved();
    void navigateToPaymentView();
}
