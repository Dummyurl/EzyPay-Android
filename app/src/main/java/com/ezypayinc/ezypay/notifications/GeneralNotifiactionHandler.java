package com.ezypayinc.ezypay.notifications;

import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.google.firebase.messaging.RemoteMessage;
import android.support.v7.app.AppCompatActivity;

public class GeneralNotifiactionHandler implements INotificationHandler {


    @Override
    public void notificationAction(final RemoteMessage notification, final AppCompatActivity currentActivity) {
        if(notification.getNotification().getBody() != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DialogBuilder dialogBuilder = new DialogBuilder(currentActivity);
                    dialogBuilder.defaultAlertDialog(notification.getNotification().getBody());
                }
            });
        }
    }

    @Override
    public CustomNotification parseNotification(RemoteMessage notification) {
        CustomNotification customNotification = null;
        if(notification.getNotification() != null) {
            customNotification = new CustomNotification();
            customNotification.setTitle(notification.getNotification().getTitle());
            customNotification.setBody(notification.getNotification().getBody());
            customNotification.setCategory(0);
        }
        return customNotification;
    }
}
