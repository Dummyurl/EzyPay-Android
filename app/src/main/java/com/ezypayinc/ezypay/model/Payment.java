package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;


public class Payment extends RealmObject implements Parcelable {

    private int id;
    private float cost;
    private int employeeId;
    private boolean isCanceled;
    private String paymentDate;
    private int tableNumber;
    private float userCost;
    private int userId;

    private User commerce;
    private Currency currency;
    private RealmList<Friend> friends;

    public Payment(){
        friends = new RealmList<>();
        commerce = new User();
        currency = new Currency();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public float getUserCost() {
        return userCost;
    }

    public void setUserCost(float userCost) {
        this.userCost = userCost;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getCommerce() {
        return commerce;
    }

    public void setCommerce(User commerce) {
        this.commerce = commerce;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public RealmList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(RealmList<Friend> friends) {
        this.friends = friends;
    }

    public JSONObject toJSON() throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return new JSONObject(json);
    }

    public Payment(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeValue(currency);
        parcel.writeFloat(cost);
        parcel.writeInt(employeeId);
        parcel.writeInt(isCanceled ? 1 : 0);
        parcel.writeString(paymentDate);
        parcel.writeInt(tableNumber);
        parcel.writeFloat(userCost);
        parcel.writeInt(userId);
        parcel.writeValue(commerce);
        parcel.writeTypedList(friends);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        currency = (Currency) in.readValue(Currency.class.getClassLoader());
        cost = in.readFloat();
        employeeId = in.readInt();
        isCanceled = in.readInt() == 1;
        paymentDate = in.readString();
        tableNumber = in.readInt();
        userCost = in.readFloat();
        userId = in.readInt();
        commerce = (User) in.readValue(User.class.getClassLoader());
        friends = friends == null ? new RealmList<Friend>() : friends;
        in.readTypedList(friends, Friend.CREATOR);
    }

    public static Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>(){
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };
}
