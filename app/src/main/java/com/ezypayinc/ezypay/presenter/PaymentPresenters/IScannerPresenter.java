package com.ezypayinc.ezypay.presenter.PaymentPresenters;


public interface IScannerPresenter {

    void validatePayment();
    void addPayment(String qrString);
    void deletePayment();
    void onDestroy();
}
