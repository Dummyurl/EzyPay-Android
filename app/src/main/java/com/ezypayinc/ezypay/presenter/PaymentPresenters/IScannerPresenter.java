package com.ezypayinc.ezypay.presenter.PaymentPresenters;


public interface IScannerPresenter {

    void validateTicket();
    void addTicket(String qrString);
    void deleteTicket();
    void onDestroy();
}
