package com.ezypayinc.ezypay.presenter.SettingsPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditPhoneView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.google.gson.JsonElement;

import java.util.List;

public class EditUserPhonePresenter implements IEditUserPhonePresenter {

    private IEditPhoneView mView;

    public EditUserPhonePresenter(IEditPhoneView view) {
        mView = view;
    }

    @Override
    public void getPhoneCodeList() {
        mView.showProgressDialog();
        final UserManager manager = new UserManager();
        manager.getPhoneCodes(new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                List<PhoneCode> phoneCodeList = manager.parsePhoneCode(response);
                mView.loadPhoneCodeList(phoneCodeList);
                mView.dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.dismissProgressDialog();
                mView.onNetworkError(error);
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
