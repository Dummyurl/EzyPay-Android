package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews;

import com.ezypayinc.ezypay.model.User;

public interface IEmployeeDetailView {

    void showProgressDialog();
    void dismissProgressDialog();
    void setupEmployee(User user);
    void goToEmployeeList();
    void onNetworkError(Object error);
    void errorNameRequired();
    void errorLastNameRequired();
    void errorUserNameRequired();
    void errorPasswordRequired();

}
