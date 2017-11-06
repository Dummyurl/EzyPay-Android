package com.ezypayinc.ezypay.services.notifications;


import android.util.Log;

import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.notifications.INotificationHandler;
import com.ezypayinc.ezypay.notifications.NotificationFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        Log.d("From notification", from);

        int category = 1;
        if (remoteMessage.getNotification() != null && remoteMessage.getData().size() > 0) {
            category = Integer.parseInt(remoteMessage.getNotification().getTag());
        }

        EzyPayApplication app = (EzyPayApplication) getApplication();
        INotificationHandler handler = NotificationFactory.initNotificationHandler(category);
        handler.notificationAction(remoteMessage, app.getCurrentActivity());
    }
}
