package com.ezypayinc.ezypay.presenter.CommercePresenter;


public interface IPaymentDetailPresenter {
    void getCurrencies();
    void savePaymentCost(float cost, int tableNumber, int currencyIndex, boolean shouldSendNotification);
}
