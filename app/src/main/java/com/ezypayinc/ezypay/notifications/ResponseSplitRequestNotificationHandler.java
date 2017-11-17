package com.ezypayinc.ezypay.notifications;

import com.ezypayinc.ezypay.controllers.userNavigation.payment.PaymentMainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.PaymentFragment;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.google.firebase.messaging.RemoteMessage;
import com.ezypayinc.ezypay.manager.PaymentManager;
import android.support.v7.app.AppCompatActivity;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import android.content.DialogInterface;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.google.gson.JsonElement;
import android.content.Intent;
import com.ezypayinc.ezypay.R;


public class ResponseSplitRequestNotificationHandler implements INotificationHandler {

    private User mUser;

    public ResponseSplitRequestNotificationHandler() {
        mUser = UserSingleton.getInstance().getUser();
    }

    @Override
    public void notificationAction(final RemoteMessage notification, final AppCompatActivity currentActivity) {
        if(notification.getData() != null && notification.getNotification().getBody() != null) {
            final int paymentId = Integer.parseInt(notification.getData().get("paymentId"));
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getPayment(paymentId, notification, currentActivity);
                }
            });

        }
    }

    private void getPayment(final int paymentId, final RemoteMessage notification, final AppCompatActivity currentActivity) {
        final PaymentManager manager = new PaymentManager();
        manager.getActivePaymentByUser(mUser, new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                Payment payment = manager.parsePayment(response);
                if(payment.getId() == paymentId) {
                    manager.updatePayment(payment);
                    displayAlert(payment, notification, currentActivity);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void displayAlert(final Payment payment, RemoteMessage notification, final AppCompatActivity currentActivity) {
        String title = notification.getNotification().getTitle();
        String message = notification.getNotification().getBody();
        final DialogBuilder builder = new DialogBuilder(currentActivity, title, message, false);
        builder.buildAlertDialog();
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismissAlertDialog();
                splitResponseAction(payment, currentActivity);
            }
        });

        builder.showAlertDialog();
    }


    private void splitResponseAction(Payment payment, AppCompatActivity currentActivity) {
        if(currentActivity instanceof PaymentMainActivity) {
            PaymentFragment fragment = (PaymentFragment) currentActivity.getSupportFragmentManager().
                    findFragmentByTag(PaymentMainActivity.PAYMENT_FRAGMENT_TAG);
            if(fragment != null && fragment.isVisible()) {
                fragment.updateData(payment);
            } else {
                PaymentFragment paymentFragment = PaymentFragment.newInstance(payment);
                currentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_user, paymentFragment, PaymentMainActivity.PAYMENT_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            Intent intent = new Intent(currentActivity, MainUserActivity.class);
            currentActivity.startActivity(intent);
        }
    }

    @Override
    public CustomNotification parseNotification(RemoteMessage notification) {
        return null;
    }
}
