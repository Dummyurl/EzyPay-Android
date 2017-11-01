package com.ezypayinc.ezypay.database;

import com.ezypayinc.ezypay.model.LocalToken;

import io.realm.Realm;

public class DeviceTokenData {

    private void saveLocalToken(LocalToken localToken) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(localToken);
        realm.commitTransaction();
    }

    public LocalToken getLocalToken() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        LocalToken localToken = realm.where(LocalToken.class).findFirst();
        realm.commitTransaction();
        return localToken;
    }

    private void deleteLocalToken() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        LocalToken result = realm.where(LocalToken.class).findFirst();
        if(result != null) {
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public void updateLocalToken(LocalToken localToken) {
        deleteLocalToken();
        saveLocalToken(localToken);
    }
}
