package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;

import java.util.List;

public interface IContactsListPresenter {

    void getContacts();
    void filterContacts(String query, List<Friend> friendsList);
    void removeFriendsFromPayment(Payment payment);
    void onDestroy();
}
