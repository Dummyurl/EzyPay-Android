package com.ezypayinc.ezypay.presenter.LoginPresenters;

public interface ISignInPaymentInformationPresenter {

    void populateSpinners();
    boolean validateFields(String cardNumber, String cvv);
    void registerRecord(String cardNumber, int cvv, int moth, int year);
    void onDestroy();
}
