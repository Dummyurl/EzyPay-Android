package com.ezypayinc.ezypay.presenter.SettingsPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditUserPasswordView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.google.gson.JsonElement;

import org.json.JSONException;


public class EditUserPasswordPresenter implements IEditUserPasswordPresenter {

    private IEditUserPasswordView mView;

    public EditUserPasswordPresenter(IEditUserPasswordView view) {
        mView = view;
    }

    @Override
    public void updatePassword(String newPassword) {
        mView.showProgressDialog();
        UserManager manager = new UserManager();
        try {
            manager.updatePassword(newPassword, UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    mView.dismissProgressDialog();
                    mView.goBack();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.dismissProgressDialog();
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
