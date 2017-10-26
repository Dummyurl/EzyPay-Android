package com.ezypayinc.ezypay.presenter.PaymentPresenters;


import android.content.Context;
import android.widget.ImageView;

public interface IScannerPresenter {

    void validatePayment();
    void addPayment(String qrString);
    void deletePayment();
    void loadCommerceImage(Context context, ImageView imageView);
    void callWaiter();
    void sendBillRequest();
    void splitPayment();
    void onDestroy();
}
