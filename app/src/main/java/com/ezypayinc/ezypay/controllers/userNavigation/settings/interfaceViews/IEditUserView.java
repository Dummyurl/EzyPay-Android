package com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews;

/**
 * Created by gustavoquesada on 1/23/17.
 */

public interface IEditUserView {

    void setErrorMessage(int component, int errorMessage);
    void onNetworkError(Object error);
    void navigateToSettingsView();
    void showProgressDialog();
    void hideProgressDialog();
}
