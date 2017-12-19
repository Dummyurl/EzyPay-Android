package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews;


public interface ICommerceHomeView {

    void showProgressDialog();
    void hideProgressDialog();
    void displayUserInformation(String avatar, String username, String viewTitle);
}
