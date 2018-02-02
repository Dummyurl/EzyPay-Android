package com.ezypayinc.ezypay.notifications;

import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.google.firebase.messaging.RemoteMessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class GeneralNotificationHandler implements INotificationHandler {


    @Override
    public void notificationAction(final CustomNotification notification, final AppCompatActivity currentActivity) {
        if(notification != null && currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogBuilder dialogBuilder = new DialogBuilder(currentActivity);
                    dialogBuilder.defaultAlertDialog(notification.getBody());
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
            return customNotification;
        }
        return null;
    }

    @Override
    public CustomNotification parseNotification(RemoteMessage notification) {
        if(notification != null && notification.getNotification() != null) {
            CustomNotification customNotification = new CustomNotification();
            customNotification.setBody((notification.getNotification().getBody()));
            return customNotification;
        }
        return  null;
    }
}
