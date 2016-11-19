package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 11/16/16.
 */

public class Contact {

    private String mName;
    private String mPhoneNumber;

    public Contact(String name, String phoneNumber) {
        this.mName = name;
        this.mPhoneNumber = phoneNumber;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public boolean equals(Object x) {
        Contact contact = (Contact) x;
        if(contact.getName().equalsIgnoreCase(mName)) {
            return true;
        }
        return false;
    }
}
