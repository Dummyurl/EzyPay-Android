package com.ezypayinc.ezypay.notifications;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.base.UserSingleton;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.HashMap;

public class SplitRequestNotificationHandler implements INotificationHandler {

    private User mUser;
    private static final String NETWORK_ERROR = "ERROR_GETTING_PAYMENT";
    private static final String UPDATE_ERROR = "ERROR_UPDATING_PAYMENT";
    private static final String NOTIFICATION_ERROR = "ER_SENDING_NOTIFICATION";
    private static final String NOTIFICATION_RESPONSE = "NOTIFICATION_RESPONSE";
    private static final String SUCCESS = "SUCCESS";
    private static final String PAYMENT_KEY = "paymentId";
    private static final String FRIEND_KEY = "friendId";
    private static final String USER_ID = "userId";

    public SplitRequestNotificationHandler() {
        mUser = UserSingleton.getInstance().getUser();
    }

    @Override
    public void notificationAction(final CustomNotification notification, final AppCompatActivity currentActivity) {
        if(notification != null && notification.getData() != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final int friendId = Integer.parseInt(notification.getData().get("friendId"));
                    if(mUser != null && mUser.getId() == friendId) {
                        getPaymentById(notification, currentActivity);
                    }
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
            values.put(FRIEND_KEY, notification.getString(FRIEND_KEY));
            values.put(USER_ID, notification.getString(USER_ID));
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
        final int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
        final int friendId = Integer.parseInt(notification.getData().get(FRIEND_KEY));
        final int clientId = Integer.parseInt(notification.getData().get(USER_ID));
        String title = notification.getNotification().getTitle();
        String message = notification.getNotification().getBody();
        customNotification.setBody(message);
        customNotification.setTitle(title);
        HashMap<String, String> values = new HashMap<>();
        values.put(PAYMENT_KEY, String.valueOf(paymentId));
        values.put(FRIEND_KEY, String.valueOf(friendId));
        values.put(USER_ID, String.valueOf(clientId));
        customNotification.setData(values);
        return customNotification;
    }

    private void getPaymentById(final CustomNotification notification, final AppCompatActivity currentActivity) {
        PaymentManager manager = new PaymentManager();
        final int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
        manager.getPaymentById(paymentId, mUser.getToken(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                JsonObject object = response.getAsJsonObject();
                if(object.get("isCanceled").getAsInt() == 0) {
                    displayAlert(notification, currentActivity);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(NETWORK_ERROR, error.getMessage());
            }
        });
    }

    private void displayAlert(final CustomNotification notification, AppCompatActivity currentActivity) {
        String title = notification.getTitle();
        String message = notification.getBody();
        final DialogBuilder builder = new DialogBuilder(currentActivity, title, message, false);
        builder.buildAlertDialog();
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismissAlertDialog();
                responseRequest(notification, -1);
            }
        });
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismissAlertDialog();
                responseRequest(notification, 1);
            }
        });
        builder.showAlertDialog();
    }

    private void responseRequest(final CustomNotification notification, final int userResponse) {
        int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
        PaymentManager manager = new PaymentManager();
        try {
            manager.updateUserPayment(mUser, paymentId, userResponse, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    responseSplitRequestNotification(notification, userResponse);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(UPDATE_ERROR, error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseSplitRequestNotification(CustomNotification notification, int response) {
        PushNotificationsManager manager = new PushNotificationsManager();
        int clientId = Integer.parseInt(notification.getData().get(USER_ID));
        int paymentId = Integer.parseInt(notification.getData().get(PAYMENT_KEY));
        try {
            manager.responseSplitNotification(mUser, paymentId, response, clientId, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    Log.d(NOTIFICATION_RESPONSE, SUCCESS);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(NOTIFICATION_ERROR, error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
