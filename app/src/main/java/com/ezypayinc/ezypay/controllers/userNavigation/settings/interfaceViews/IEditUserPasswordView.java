package com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews;

public interface IEditUserPasswordView {
    void goBack();
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
}
