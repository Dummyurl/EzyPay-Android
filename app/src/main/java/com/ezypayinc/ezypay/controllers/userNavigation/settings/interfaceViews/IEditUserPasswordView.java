package com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews;

/**
 * Created by gustavoquesada on 11/16/17.
 */

public interface IEditUserPasswordView {
    void goBack();
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
}
