package com.ezypayinc.ezypay.model;

import io.realm.RealmObject;

/**
 * Created by gustavoquesada on 10/22/17.
 */

public class Currency extends RealmObject {

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
}
