package com.ezypayinc.ezypay.presenter.LoginPresenters;

public interface ILoginPresenter {

    void loginMethod(String email, String password);

    void validateUser();

    void onDestroy();
}
