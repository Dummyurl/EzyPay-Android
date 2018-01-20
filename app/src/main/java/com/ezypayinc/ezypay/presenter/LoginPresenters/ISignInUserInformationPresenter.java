package com.ezypayinc.ezypay.presenter.LoginPresenters;


public interface ISignInUserInformationPresenter {

    void validateUser();
    void registerUser(String username, String lastName, String phoneNumber, String email, String password, String code);
    void onDestroy();
}
