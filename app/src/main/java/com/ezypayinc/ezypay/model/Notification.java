package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 11/17/16.
 */

public class Notification {

    private int mNotificationId;
    private String mRestaurantName;
    private String mRestaurantImage;
    private String mUserName;
    private float mCost;

    public Notification(int notificationId, String restaurantName, String restaurantImage, String userName, float cost) {
        mNotificationId = notificationId;
        mRestaurantName = restaurantName;
        mRestaurantImage = restaurantImage;
        mUserName = userName;
        mCost = cost;
    }

    public int getNotificationId() {
        return mNotificationId;
    }

    public void setNotificationId(int notificationId) {
        mNotificationId = notificationId;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        mRestaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return mRestaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        mRestaurantImage = restaurantImage;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public float getCost() {
        return mCost;
    }

    public void setCost(float cost) {
        mCost = cost;
    }
}
