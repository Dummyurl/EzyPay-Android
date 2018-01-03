package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews;


import com.ezypayinc.ezypay.model.User;

import java.util.List;

public interface IEmployeeListView {
    void showProgressDialog();
    void dismissProgressDialog();
    void populateEmployeeList(List<User> employeeList);
    void onNetworkError(Object error);
}
