package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees;

enum EmployeeDetailViewType {
    VIEW_EMPLOYEE(1), ADD_EMPLOYEE(2), EDIT_EMPLOYEE(3);

    private final int mType;

    EmployeeDetailViewType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
