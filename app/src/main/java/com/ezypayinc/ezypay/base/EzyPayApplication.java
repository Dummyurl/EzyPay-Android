package com.ezypayinc.ezypay.base;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EzyPayApplication extends Application {

    private static EzyPayApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /*init database*/
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }

    public static EzyPayApplication getInstance() {
        return instance;
    }
}
