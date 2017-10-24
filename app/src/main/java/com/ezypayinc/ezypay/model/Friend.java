package com.ezypayinc.ezypay.model;

import io.realm.RealmObject;

/**
 * Created by gustavoquesada on 10/22/17.
 */

public class Friend extends RealmObject {

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
}
