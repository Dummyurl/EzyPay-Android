package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 11/5/16.
 */

public class User {

    public int mId;
    public String mName, mLastname, mPhoneNumber, mEmail, mPassword,
            mCardNumber, mCvv, mExpirationDate;

    public User(){
    }

    public User(int id, String name, String lastname, String phoneNumber, String email, String password,
                String cardNumber, String cvv, String expirationDate) {
        mId = id;
        mName = name;
        mLastname = lastname;
        mPhoneNumber = phoneNumber;
        mEmail = email;
        mPassword = password;
        mCardNumber = cardNumber;
        mCvv = cvv;
        mExpirationDate = expirationDate;
    }
}
