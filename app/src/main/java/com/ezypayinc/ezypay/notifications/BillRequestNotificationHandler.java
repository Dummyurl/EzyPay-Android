package com.ezypayinc.ezypay.notifications;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.controllers.commerceNavigation.navigation.MainCommerceActivity;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.PaymentCommerceMainActivity;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.PaymentDetailFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonElement;

import java.util.HashMap;

public class BillRequestNotificationHandler implements INotificationHandler {

    private User mUser;
    private static final String ERROR_TAG = "ERROR GETTING PAYMENT";
    private static final String PAYMENT_KEY = "paymentId";

    public BillRequestNotificationHandler() {
        mUser = UserSingleton.getInstance().getUser();
    }


    @Override
    public void notificationAction(final CustomNotification notification, final AppCompatActivity currentActivity) {
        if(notification != null) {
            final int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getPayment(paymentId, notification, currentActivity);
                }
            });
        }
    }


    @Override
    public CustomNotification parseNotification(Bundle notification) {
        if(notification != null) {
            CustomNotification customNotification = new CustomNotification();
            int category = Integer.parseInt(notification.getString("category"));
            customNotification.setBody(notification.getString("message"));
            customNotification.setTitle(notification.getString("title"));
            customNotification.setCategory(category);
            HashMap<String, String> values = new HashMap<>();
            values.put(PAYMENT_KEY, notification.getString(PAYMENT_KEY));
            customNotification.setData(values);
            return customNotification;
        }
        return null;
    }

    @Override
    public CustomNotification parseNotification(RemoteMessage notification) {
        if(notification == null || notification.getData() == null || notification.getNotification() == null) {
            return null;
        }
        CustomNotification customNotification = new CustomNotification();
        int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
        String title = notification.getNotification().getTitle();
        String message = notification.getNotification().getBody();
        customNotification.setBody(message);
        customNotification.setTitle(title);
        HashMap<String, String> values = new HashMap<>();
        values.put(PAYMENT_KEY, String.valueOf(paymentId));
        customNotification.setData(values);
        return customNotification;
    }

    private void getPayment(final int paymentId, final CustomNotification notification, final AppCompatActivity currentActivity) {
        if (mUser != null) {
            final PaymentManager manager = new PaymentManager();
            manager.getPaymentById(paymentId,mUser.getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    Payment payment = manager.parsePayment(response);
                    if(payment != null && payment.getId() == paymentId) {
                        manager.updatePayment(payment);
                        displayAlert(payment, notification, currentActivity);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(ERROR_TAG, error.getMessage());
                }
            });
        }
    }

    private void displayAlert(final Payment payment, CustomNotification notification, final AppCompatActivity currentActivity) {
        String title = notification.getTitle();
        String message = notification.getBody();
        final DialogBuilder builder = new DialogBuilder(currentActivity, title, message, false);
        builder.buildAlertDialog();
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismissAlertDialog();
                goToFragment(payment, currentActivity);
            }
        });
        builder.showAlertDialog();
    }

    private void goToFragment(Payment payment, AppCompatActivity currentActivity) {
            Intent intent = new Intent(currentActivity, PaymentCommerceMainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(PaymentCommerceMainActivity.PAYMENT_KEY, payment);
            bundle.putBoolean(PaymentCommerceMainActivity.PAYMENT_FROM_NOTIFICATION, true);
            intent.putExtras(bundle);
            currentActivity.startActivity(intent);
    }
}
