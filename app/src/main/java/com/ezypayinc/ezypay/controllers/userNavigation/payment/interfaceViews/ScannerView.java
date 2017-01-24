package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;

import com.ezypayinc.ezypay.model.Ticket;


public interface ScannerView {

    void showRestaurantDetail(Ticket ticket);
    void showScannerView();
    void onNetworkError(Object error);
}
