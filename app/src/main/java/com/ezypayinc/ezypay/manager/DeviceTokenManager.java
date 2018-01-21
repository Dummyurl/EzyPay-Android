package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.DeviceTokenServiceClient;
import com.ezypayinc.ezypay.database.DeviceTokenData;
import com.ezypayinc.ezypay.model.LocalToken;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class DeviceTokenManager {

    /** DATA METHODS **/
    public LocalToken getLocalToken() {
        DeviceTokenData data = new DeviceTokenData();
        return data.getLocalToken();
    }

    public void updateLocalToken(LocalToken localToken) {
        DeviceTokenData data = new DeviceTokenData();
        data.updateLocalToken(localToken);
    }

    /** WEB SERVICES METHODS **/
    public void registerDeviceToken(LocalToken localToken, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        DeviceTokenServiceClient service = new DeviceTokenServiceClient();
        service.registerDeviceToken(localToken, token, successHandler, failureHandler);
    }

    public void deleteDeviceToken(String deviceId, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        DeviceTokenServiceClient service = new DeviceTokenServiceClient();
        service.deleteDeviceToken(deviceId, user, successHandler, failureHandler);
    }
}
