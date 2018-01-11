package com.ezypayinc.ezypay.base;

import android.graphics.Bitmap;

import com.ezypayinc.ezypay.model.User;

public class UserSingleton {

    private static UserSingleton instance;
    private User user;
    private Bitmap userProfilePhoto;
    private int mTablesQuantity;

    public UserSingleton() {

    }

    public static UserSingleton getInstance() {
        if(instance == null){
            instance = new UserSingleton();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUserProfilePhoto(Bitmap image) {
        userProfilePhoto = image;
    }

    public Bitmap getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setTablesQuantity(int tablesQuantity) {
        mTablesQuantity = mTablesQuantity;
    }

    public int getTablesQuantity() {
        return mTablesQuantity;
    }
}
