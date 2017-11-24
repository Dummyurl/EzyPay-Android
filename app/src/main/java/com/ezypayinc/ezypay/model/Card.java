package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Card extends RealmObject implements Parcelable{

    private int id;
    private int userId;
    private String cardNumber;
    private int ccv;
    private String expirationDate;
    private int isFavorite;
    private int cardVendor;
    private String token;
    private int serverId;

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCcv() {
        return ccv;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }

    public int getCardVendor() {
        return cardVendor;
    }

    public void setCardVendor(int cardVendor) {
        this.cardVendor = cardVendor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
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
        parcel.writeInt(userId);
        parcel.writeString(cardNumber);
        parcel.writeInt(ccv);
        parcel.writeString(expirationDate);
        parcel.writeInt(isFavorite);
        parcel.writeInt(cardVendor);
        parcel.writeString(token);
        parcel.writeInt(serverId);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        cardNumber = in.readString();
        ccv = in.readInt();
        expirationDate = in.readString();
        isFavorite = in.readInt();
        cardVendor = in.readInt();
        token = in.readString();
        serverId = in.readInt();
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
