package com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews;


public interface IPaymentDetailView {

    void showProgressDialog();
    void dismissProgressDialog();
    void loadCurrencies(String []currencyName);
    void onNetworkError(Object error);
}
