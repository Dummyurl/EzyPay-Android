package com.ezypayinc.ezypay.presenter.SettingsPresenters;

public interface IEditUserPresenter {

    void updateUser(byte [] encodedImage, String name, String lastName, String email, String phoneNumber);
    void onDestroy();

}
