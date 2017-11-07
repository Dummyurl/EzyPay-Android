package com.ezypayinc.ezypay.notifications;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.base.UserSingleton;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

public class SplitRequestNotificationHandler implements INotificationHandler {

    private User mUser;
    private static final String NETWORK_ERROR = "ERROR_GETTING_PAYMENT";
    private static final String UPDATE_ERROR = "ERROR_UPDATING_PAYMENT";
    private static final String NOTIFICATION_ERROR = "ER_SENDING_NOTIFICATION";
    private static final String NOTIFICATION_RESPONSE = "NOTIFICATION_RESPONSE";
    private static final String SUCCESS = "SUCCESS";

    public SplitRequestNotificationHandler() {
        mUser = UserSingleton.getInstance().getUser();
    }

    @Override
    public void notificationAction(final RemoteMessage notification, final AppCompatActivity currentActivity) {
        try {
            if (notification.getData() != null && notification.getNotification().getBody() != null) {
                currentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final int paymentId = Integer.parseInt(notification.getData().get("paymentId"));
                        final int friendId = Integer.parseInt(notification.getData().get("friendId"));
                        if(mUser != null && mUser.getId() == friendId) {
                            getPaymentById(paymentId, notification, currentActivity);
                        }
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void getPaymentById(int paymentId, final RemoteMessage notification, final AppCompatActivity currentActivity) {
        PaymentManager manager = new PaymentManager();
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

    private void displayAlert(final RemoteMessage notification, AppCompatActivity currentActivity) {
        String title = notification.getNotification().getTitle();
        String message = notification.getNotification().getBody();
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

    private void responseRequest(final RemoteMessage notification, final int userResponse) {
        int paymentId = Integer.parseInt(notification.getData().get("paymentId"));
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

    private void responseSplitRequestNotification(RemoteMessage notification, int response) {
        PushNotificationsManager manager = new PushNotificationsManager();
        int clientId = Integer.parseInt(notification.getData().get("userId"));
        int paymentId = Integer.parseInt(notification.getData().get("paymentId"));
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
