package com.ezypayinc.ezypay.notifications;


import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.base.UserSingleton;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.messaging.RemoteMessage;

public class SplitRequestNotificationHandler implements INotificationHandler {

    private User mUser;

    public SplitRequestNotificationHandler() {
        mUser = UserSingleton.getInstance().getUser();
    }

    @Override
    public void notificationAction(RemoteMessage notification, AppCompatActivity currentActivity) {
       /* int64_t paymentId = [[notification objectForKey:@"paymentId"] integerValue];
        int64_t friendId = [[notification objectForKey:@"friendId"] integerValue];
        if(self.user && self.user.id == friendId ) {
        [self getPayment:paymentId notification:notification];
        }*/
       if (notification.getData() != null && notification.getNotification().getBody() != null) {
       }

    }
}
