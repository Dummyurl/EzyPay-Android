package com.ezypayinc.ezypay.base;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EzyPayApplication extends Application {

    private static EzyPayApplication instance;
    private static final String dateFormat = "yyyy-MM-dd";

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

    public String getCurrentDate() {
        SimpleDateFormat simpleDate =  new SimpleDateFormat(dateFormat);
        String strDt = simpleDate.format(new Date());
        return strDt;
    }

    public String getDeviceUUID() {
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }
}
