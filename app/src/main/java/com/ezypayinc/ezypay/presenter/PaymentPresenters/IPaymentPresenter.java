package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.model.Payment;

public interface IPaymentPresenter {

    void performPayment(Payment payment);
    void onDestroy();
}
