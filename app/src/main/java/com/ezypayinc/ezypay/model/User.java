package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 11/5/16.
 */

public class User {

    public int mId;
    public String mName, mLastname, mPhoneNumber, mEmail, mPassword;

    public User(){
    }

    public User(int id, String name, String lastname, String phoneNumber, String email, String password) {
        mId = id;
        mName = name;
        mLastname = lastname;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mPassword = password;
    }
}
