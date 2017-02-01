package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Card extends RealmObject implements Parcelable{

    private int id;
    private int cvv;
    private int month;
    private int year;
    private String number;

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Card(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(cvv);
        parcel.writeInt(month);
        parcel.writeInt(year);
        parcel.writeString(number);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        cvv = in.readInt();
        month = in.readInt();
        year = in.readInt();
        number = in.readString();
    }

    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        public Card[] newArray(int size) {
            return new Card[size];
        }
    };
}
