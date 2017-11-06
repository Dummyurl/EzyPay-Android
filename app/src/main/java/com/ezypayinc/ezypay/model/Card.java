package com.ezypayinc.ezypay.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;

import io.realm.RealmObject;

public class Card extends RealmObject implements Parcelable{

    private int id;
    private int userId;
    private String cardNumber;
    private int cvv;
    private String expirationDate;
    private boolean isFavorite;
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

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
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
        parcel.writeInt(cvv);
        parcel.writeString(expirationDate);
        parcel.writeInt(isFavorite ? 1 : 0);
        parcel.writeInt(cardVendor);
        parcel.writeString(token);
        parcel.writeInt(serverId);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        cardNumber = in.readString();
        cvv = in.readInt();
        expirationDate = in.readString();
        isFavorite = in.readInt() == 1;
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
