package com.ezypayinc.ezypay.services.notifications;


import android.util.Log;

import com.ezypayinc.ezypay.notifications.INotificationHandler;
import com.ezypayinc.ezypay.notifications.NotificationFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d("From notification", from);

        if (remoteMessage.getNotification() != null && remoteMessage.getData().size() > 0) {
            int category = Integer.parseInt(remoteMessage.getData().get("category").toString());
            INotificationHandler handler = NotificationFactory.initNotificationHandler(category);
            handler.notificationAction(remoteMessage);
        }
    }
}
