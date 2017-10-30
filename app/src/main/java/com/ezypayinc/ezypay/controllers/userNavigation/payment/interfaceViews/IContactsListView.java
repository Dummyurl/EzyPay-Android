package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;

import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.User;

import java.util.List;


public interface IContactsListView {

    void displayListOfContacts(List<Friend> contacts);
    void setFilter(List<Friend> contacts);
    void onNetworkError(Object error);
}
