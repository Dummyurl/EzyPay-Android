package com.ezypayinc.ezypay.presenter.PaymentPresenters;


import com.ezypayinc.ezypay.model.Ticket;

/**
 * Created by gustavoquesada on 12/16/16.
 */

public interface IScannerPresenter {

    void validateTicket();
    void addTicket(String qrString);
    void onDestroy();
}
