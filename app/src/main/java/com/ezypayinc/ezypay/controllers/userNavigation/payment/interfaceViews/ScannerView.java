package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;

import com.ezypayinc.ezypay.model.Ticket;

/**
 * Created by gustavoquesada on 12/16/16.
 */

public interface ScannerView {

    void showRestaurantDetail(Ticket ticket);
    void showScannerView();
    void onNetworkError(Object error);
}
