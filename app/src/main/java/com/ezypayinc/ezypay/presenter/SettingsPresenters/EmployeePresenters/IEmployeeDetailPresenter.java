package com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters;


import com.ezypayinc.ezypay.model.User;

public interface IEmployeeDetailPresenter {
    void getEmployeeDetail(int employeeId);
    void insertEmployee(User user);
    void updateEmployee(User user);
}
