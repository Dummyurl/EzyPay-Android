package com.ezypayinc.ezypay.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;


public class LocalToken extends RealmObject {

    private static final String DEVICE_PLATFORM  = "Android";
    private String deviceId;
    private String devicePlatform;
    private String deviceToken;
    private boolean isSaved;
    private int userId;

    public LocalToken() {
        devicePlatform = DEVICE_PLATFORM;
    }

    public LocalToken(LocalToken localToken) {
        if(localToken != null) {
            deviceId = localToken.getDeviceId();
            deviceToken = localToken.getDeviceToken();
            devicePlatform = localToken.getDevicePlatform();
            isSaved = localToken.isSaved();
            userId = localToken.getUserId();
        }
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevicePlatform() {
        return devicePlatform;
    }


    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public JSONObject toJSON() throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return new JSONObject(json);
    }
}
