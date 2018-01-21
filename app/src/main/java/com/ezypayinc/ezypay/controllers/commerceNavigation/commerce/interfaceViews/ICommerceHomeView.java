package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews;


import com.ezypayinc.ezypay.model.Payment;

public interface ICommerceHomeView {

    void showProgressDialog();
    void hideProgressDialog();
    void displayUserInformation(String avatar, String username, String viewTitle);
    void displayCommerceTableListView();
    void generateQRCode(Payment payment);
}
