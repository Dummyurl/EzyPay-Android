package com.ezypayinc.ezypay.notifications;

/**
 * Created by gustavoquesada on 10/31/17.
 */

public class NotificationFactory {

    public static INotificationHandler initNotificationHandler(int category) {
        INotificationHandler handler;
        switch (category) {
            case 1:
                handler = new GeneralNotifiactionHandler();
                break;
            case 3:
                handler = new SendBillNotificationHandler();
                break;
            default:
                handler = null;
        }
        return  handler;
    }
}
