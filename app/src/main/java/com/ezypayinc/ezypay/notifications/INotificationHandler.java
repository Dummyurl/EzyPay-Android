package com.ezypayinc.ezypay.notifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.model.CustomNotification;
import com.google.firebase.messaging.RemoteMessage;


public interface INotificationHandler {
    void notificationAction(CustomNotification notification, AppCompatActivity currentActivity);
    CustomNotification parseNotification(Bundle notification);
    CustomNotification parseNotification(RemoteMessage notification);
}
