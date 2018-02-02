package com.ezypayinc.ezypay.database;

import com.ezypayinc.ezypay.model.Payment;

import io.realm.Realm;

public class PaymentData {

    public void addPayment(Payment payment) {
        deletePayment();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(payment);
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

    public Payment getPayment() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Payment payment = realm.where(Payment.class).findFirst();
        realm.commitTransaction();
        return payment;
    }

    public Payment updatePaymentDate(Payment payment, String paymentDate) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        payment.setPaymentDate(paymentDate);
        realm.commitTransaction();
        return payment;
    }
}
