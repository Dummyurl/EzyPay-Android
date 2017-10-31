package com.ezypayinc.ezypay.database;

import com.ezypayinc.ezypay.model.Payment;

import io.realm.Realm;

/**
 * Created by gustavoquesada on 10/23/17.
 */

public class PaymentData {

    public void addPayment(Payment payment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Payment paymentRealm = realm.copyToRealm(payment);
        realm.commitTransaction();
    }

    public void deletePayment() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Payment result = realm.where(Payment.class).findFirst();
        if(result != null) {
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void removeFriendsFromPayment(Payment payment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        payment.getFriends().clear();
        realm.commitTransaction();
    }

    public void updatePayment(Payment payment) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(payment);
        realm.commitTransaction();
    }
}
