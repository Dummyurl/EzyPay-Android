package com.ezypayinc.ezypay.services.notifications;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.manager.DeviceTokenManager;
import com.ezypayinc.ezypay.model.LocalToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String DEVICE_TOKEN_ERROR = "Error saving token";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);
    }


    private void registerToken(String token) {
        final LocalToken localToken = new LocalToken();
        localToken.setDeviceId(EzyPayApplication.getInstance().getDeviceUUID());
        localToken.setDeviceToken(token);
        localToken.setSaved(false);
        final DeviceTokenManager manager = new DeviceTokenManager();
        if(UserSingleton.getInstance().getUser() != null) {
            localToken.setUserId(UserSingleton.getInstance().getUser().getId());
            try {
                manager.registerDeviceToken(localToken, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        localToken.setSaved(true);
                        manager.updateLocalToken(localToken);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEVICE_TOKEN_ERROR, error.getMessage());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            localToken.setUserId(0);
            manager.updateLocalToken(localToken);
        }

    }
}
