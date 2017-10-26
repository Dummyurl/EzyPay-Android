package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by gustavoquesada on 10/22/17.
 */

public class Friend extends RealmObject implements Parcelable {

    private int id;
    private String avatar;
    private float cost;
    private String name;
    private String lastname;
    private int state;

    public Friend() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Friend(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(lastname);
        parcel.writeString(avatar);
        parcel.writeFloat(cost);
        parcel.writeInt(state);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lastname = in.readString();
        avatar = in.readString();
        cost = in.readFloat();
        state = in.readInt();
    }

    public static Parcelable.Creator<Friend> CREATOR = new Parcelable.Creator<Friend>(){
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
