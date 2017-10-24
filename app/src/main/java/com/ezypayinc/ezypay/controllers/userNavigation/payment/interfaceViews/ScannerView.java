package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;


import com.ezypayinc.ezypay.model.Payment;

public interface ScannerView {

    void showRestaurantDetail(Payment payment);
    void showScannerView();
    void onNetworkError(Object error);
}
