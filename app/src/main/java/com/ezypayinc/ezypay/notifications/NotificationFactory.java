package com.ezypayinc.ezypay.notifications;

public class NotificationFactory {

    public static INotificationHandler initNotificationHandler(int category) {
        INotificationHandler handler;
        switch (category) {
            case 1:
                handler = new GeneralNotificationHandler();
                break;
            case 2:
                handler = new BillRequestNotificationHandler();
                break;
            case 3:
                handler = new SendBillNotificationHandler();
                break;
            case 4:
                handler = new SplitRequestNotificationHandler();
                break;
            case 5:
                handler = new ResponseSplitRequestNotificationHandler();
                break;
            default:
                handler = new GeneralNotificationHandler();
                break;
        }
        return  handler;
    }
}
