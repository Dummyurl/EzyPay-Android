package com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews;


public interface ISignInBankInformationView {
    void goToHomeView();
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
    void commerceIdRequiredError();
    void accountNumberRequiredError();
    void accountNameRequiredError();
    void bankRequiredError();
}
