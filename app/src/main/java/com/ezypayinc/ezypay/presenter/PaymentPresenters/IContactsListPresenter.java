package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.model.User;

import java.util.List;

/**
 * Created by gustavoquesada on 1/14/17.
 */

public interface IContactsListPresenter {

    void getContacts();
    void filterContacts(String query, List<User> usersList);
    void onDestroy();
}
