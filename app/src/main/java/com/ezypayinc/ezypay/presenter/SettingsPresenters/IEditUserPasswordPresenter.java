package com.ezypayinc.ezypay.presenter.SettingsPresenters;

/**
 * Created by gustavoquesada on 11/16/17.
 */

public interface IEditUserPasswordPresenter {

    void updatePassword(String newPassword);
    void onDestroy();
}
