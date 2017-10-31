package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ISplitListView;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;

import org.json.JSONException;


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
    public void addFriendsToPayment(final Payment payment) {
        PaymentManager manager = new PaymentManager();
        manager.updatePayment(payment);
        try {
            manager.addPaymentToFriends(payment, UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    sendPaymentNotifications(payment);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendPaymentNotifications(Payment payment) {
        PushNotificationsManager manager = new PushNotificationsManager();
        try {
            manager.sendPaymentNotifications(payment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    mView.goToPaymentView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        mView = null;
    }
}
