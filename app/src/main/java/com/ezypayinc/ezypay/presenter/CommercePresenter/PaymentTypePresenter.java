package com.ezypayinc.ezypay.presenter.CommercePresenter;

import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IPaymentTypeView;
import com.ezypayinc.ezypay.model.Currency;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;



public class PaymentTypePresenter implements IPaymentTypePresenter {

    private IPaymentTypeView mView;

    public PaymentTypePresenter(IPaymentTypeView view) {
        mView = view;
    }

    @Override
    public void setupSyncPayAction(int tableNumber) {
        Payment payment = new Payment();
        User user = UserSingleton.getInstance().getUser();
        payment.setTableNumber(tableNumber);
        if(user.getUserType() == 4) {
            payment.setEmployeeId(user.getId());
        } else {
            payment.setEmployeeId(0);
        }
        mView.goToSyncPayAction(payment);
    }
}
