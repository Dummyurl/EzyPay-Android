package com.ezypayinc.ezypay.presenter.CommercePresenter;

import com.ezypayinc.ezypay.model.Payment;

public interface IQRCodeGeneratorPresenter {
    void generateQrCode(Payment payment);
}
