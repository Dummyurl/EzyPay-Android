package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ISplitListView;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;


public class SplitListPresenter implements ISplitListPresenter {

    private ISplitListView mView;
    public  SplitListPresenter(ISplitListView view){
        mView = view;
    }

    @Override
    public int validateQuantity(int progress, Payment payment, Friend friend) {
        float quantity = (payment.getCost() * progress) / 100;
        float quantityValidated = validatePayment(quantity, payment, friend);
        if(friend == null){
            payment.setUserCost(payment.getUserCost() - quantityValidated);
            return (int) ((payment.getUserCost() / payment.getCost()) * 100);
        } else {
            friend.setCost(friend.getCost() - quantityValidated);
            return (int) ((friend.getCost() / payment.getCost()) * 100);
        }
    }

    private float validatePayment(float quantity, Payment payment, Friend friend) {
        float currentQuantity = 0;
        if(friend == null) {
            payment.setUserCost(quantity);
        } else  {
            friend.setCost(quantity);
        }
        for (Friend currentFriend: payment.getFriends()) {
            currentQuantity += currentFriend.getCost();
        }
        currentQuantity += payment.getUserCost();
        if(currentQuantity  <= payment.getCost()) {
            float paymentShortage = payment.getCost() - currentQuantity;
            mView.setPaymentData(payment, paymentShortage);
            return 0;
        } else {
            mView.setPaymentData(payment, 0);
            return currentQuantity - payment.getCost();
        }
    }

    @Override
    public void changePaymentQuantity(float quantity, SplitAdapter.SplitViewHolder cell, Payment payment) {
        int progress = (int) ((quantity / payment.getCost()) * 100);
        cell.paymentSeekBar.setProgress(progress);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
