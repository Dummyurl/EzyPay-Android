package com.ezypayinc.ezypay.presenter.LoginPresenters;

/**
 * Created by gustavoquesada on 11/25/16.
 */

public interface ISignInPaymentInformationPresenter {

    void populateSpinners();
    boolean validateFields(String cardNumber, String cvv);
    void registerRecord(String cardNumber, int cvv, int moth, int year);
    void onDestroy();
}
