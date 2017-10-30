package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;


public interface ISplitListPresenter {

    int validateQuantity(int progress, Payment payment, Friend friend);
    void changePaymentQuantity(float quantity, SplitAdapter.SplitViewHolder cell, Payment payment);
    void onDestroy();
}
