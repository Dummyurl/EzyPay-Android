package com.ezypayinc.ezypay.presenter.SettingsPresenters;

public interface IEditUserPresenter {

    void uploadImage(byte [] encodedImage);
    void updateUser(String name, String lastName, String email, String phoneNumber);
    void onDestroy();

}
