package com.ezypayinc.ezypay.presenter.LoginPresenters;

import com.ezypayinc.ezypay.model.User;

public interface ILoginPresenter {

    void loginMethod(String email, String password);
    void validateFacebookLogin(User user);
    void onDestroy();
}
