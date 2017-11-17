package com.ezypayinc.ezypay.model;


import java.util.HashMap;

public class CustomNotification {

    private String mTitle;
    private String mBody;
    private int mCategory;
    private HashMap<String, String> mData;

    public CustomNotification() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }

    public HashMap<String, String> getData() {
        return mData;
    }

    public void setData(HashMap<String, String> data) {
        mData = data;
    }
}
