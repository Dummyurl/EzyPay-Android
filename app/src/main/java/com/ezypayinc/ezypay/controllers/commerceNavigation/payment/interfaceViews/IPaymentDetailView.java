package com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews;


import com.ezypayinc.ezypay.model.Payment;

public interface IPaymentDetailView {

    void showProgressDialog();
    void dismissProgressDialog();
    void loadCurrencies(String []currencyName);
    void navigateToQRFragment(Payment payment);
    void navigateToPreviousView();
    void onNetworkError(Object error);
}
