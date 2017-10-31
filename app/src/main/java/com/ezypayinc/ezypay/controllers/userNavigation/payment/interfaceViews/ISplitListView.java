package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;

import com.ezypayinc.ezypay.model.Payment;


public interface ISplitListView {

    void setPaymentData(Payment payment, float shortage);
    void goToPaymentView();
    void onNetworkError(Object object);
}
