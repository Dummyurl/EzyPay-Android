package com.ezypayinc.ezypay.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class User extends RealmObject {

    private int id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private String token;
    private RealmList<Card> cards;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RealmList<Card> getCards() {
        return cards;
    }

    public void setCards(RealmList<Card> cards) {
        this.cards = cards;
    }

    public boolean equals(Object x) {
        User user = (User) x;
        String fullName = user.getName() + " " + user.getLastName();
        if(fullName.equalsIgnoreCase(name + " " + lastName)) {
            return true;
        }
        return false;
    }
}
