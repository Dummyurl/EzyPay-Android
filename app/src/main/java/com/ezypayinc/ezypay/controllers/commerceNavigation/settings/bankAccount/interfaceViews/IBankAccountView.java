package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.bankAccount.interfaceViews;


import com.ezypayinc.ezypay.model.BankAccount;

public interface IBankAccountView {
    void populateAccount(BankAccount bankAccount);
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
    void commerceIdRequiredError();
    void accountNumberRequiredError();
    void accountNameRequiredError();
    void bankRequiredError();
}