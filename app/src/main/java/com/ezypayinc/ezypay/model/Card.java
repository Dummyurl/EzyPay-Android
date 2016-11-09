package com.ezypayinc.ezypay.model;

import io.realm.RealmObject;

/**
 * Created by gustavoquesada on 11/6/16.
 */

public class Card extends RealmObject{

    private int id;
    private int cvv;
    private int month;
    private int year;
    private User user;
    private String cardNumber;

    public Card(){
        this.id = 0;
        this.cvv = 0;
        this.month = 0;
        this.year = 0;
        this.user = new User();
        this.cardNumber = "";
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
