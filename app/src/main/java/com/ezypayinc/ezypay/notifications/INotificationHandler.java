package com.ezypayinc.ezypay.notifications;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by gustavoquesada on 10/31/17.
 */

public interface INotificationHandler {
    void notificationAction(RemoteMessage notification);
}
