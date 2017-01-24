package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.model.User;

import java.util.List;

public interface IContactsListPresenter {

    void getContacts();
    void filterContacts(String query, List<User> usersList);
    void onDestroy();
}
