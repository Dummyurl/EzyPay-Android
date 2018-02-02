package com.ezypayinc.ezypay.presenter.CommercePresenter;


import com.ezypayinc.ezypay.model.Payment;

public interface IPaymentDetailPresenter {
    void getCurrencies();
    void savePaymentCost(float cost, int tableNumber, int currencyIndex);
    void sendBillNotification(float cost, int currencyIndex, Payment payment);
}
