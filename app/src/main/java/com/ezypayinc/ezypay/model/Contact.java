package com.ezypayinc.ezypay.model;

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
        return contact.getName().equalsIgnoreCase(mName);
    }
}
