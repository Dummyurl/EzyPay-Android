package com.ezypayinc.ezypay.base;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by gustavoquesada on 11/7/16.
 */

public class EzyPayApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
