package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 11/6/16.
 */

public class Card {

    public int mId, mCvv, month, year;
    public User mUser;
    public String mCardNumber;

    public Card(){
        mId = 0;
        mCvv = 0;
        month = 0;
        year = 0;
        mUser = new User();
        mCardNumber = mCardNumber;
    }
}
