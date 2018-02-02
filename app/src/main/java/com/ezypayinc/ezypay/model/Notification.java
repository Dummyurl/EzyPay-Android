package com.ezypayinc.ezypay.model;

import java.util.HashMap;
import java.util.Map;

public class Notification {

    private int mCategory;
    private String mTitle;
    private String mMessage;
    private Map<String, String> mValues;

    public Notification() {
        mValues = new HashMap<>();
    }

    public Notification(int category, String title, String message, Map<String, String> values) {
        mCategory = category;
        mTitle = title;
        mMessage = message;
        mValues = values;
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Map<String, String> getValues() {
        return mValues;
    }

    public void setValues(Map<String, String> values) {
        mValues = values;
    }
}
