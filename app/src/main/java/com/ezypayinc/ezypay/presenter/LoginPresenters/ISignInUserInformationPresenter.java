package com.ezypayinc.ezypay.presenter.LoginPresenters;

import com.ezypayinc.ezypay.model.User;

/**
 * Created by gustavoquesada on 11/24/16.
 */

public interface ISignInUserInformationPresenter {

    User validateUser();
    void registerUser(String username, String lastName, String phoneNumber, String email, String password);
    void onDestroy();
}
