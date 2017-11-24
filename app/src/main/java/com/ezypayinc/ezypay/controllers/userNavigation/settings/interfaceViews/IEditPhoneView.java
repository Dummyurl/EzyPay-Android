package com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews;


import com.ezypayinc.ezypay.model.PhoneCode;
import java.util.List;

public interface IEditPhoneView {

    void loadPhoneCodeList(List<PhoneCode> phoneCodeList);
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
}
