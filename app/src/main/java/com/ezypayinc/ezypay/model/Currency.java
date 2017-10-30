package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;


public class Currency extends RealmObject implements Parcelable {

    private int id;
    private String code;
    private String name;

    public Currency() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrencySymbol() {
        java.util.Currency currency = java.util.Currency.getInstance(getCode());
        return currency.getSymbol();
    }

    private Currency(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(code);
        parcel.writeString(name);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        code = in.readString();
        name = in.readString();
    }

    public static Parcelable.Creator<Currency> CREATOR = new Parcelable.Creator<Currency>(){
        public Currency createFromParcel(Parcel in) {
            return new Currency(in);
        }

        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}
