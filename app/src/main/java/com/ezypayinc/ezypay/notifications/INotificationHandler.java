package com.ezypayinc.ezypay.notifications;

import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.model.CustomNotification;
import com.google.firebase.messaging.RemoteMessage;


public interface INotificationHandler {
    void notificationAction(RemoteMessage notification, AppCompatActivity currentActivity);
    CustomNotification parseNotification(RemoteMessage notification);
}
