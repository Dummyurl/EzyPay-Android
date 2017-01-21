package com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews;

import com.ezypayinc.ezypay.model.User;

import java.util.List;

/**
 * Created by gustavoquesada on 1/14/17.
 */

public interface ContactsListView {

    void displayListOfContacts(List<User> contacts);
    void setFilter(List<User> contacts);
    void onNetworkError(Object error);
}
