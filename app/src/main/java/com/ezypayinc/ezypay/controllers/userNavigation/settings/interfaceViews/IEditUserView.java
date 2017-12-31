package com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews;


public interface IEditUserView {

    void setErrorMessage(int component, int errorMessage);
    void onNetworkError(Object error);
    void navigateToSettingsView();
    void showProgressDialog();
    void hideProgressDialog();
}
