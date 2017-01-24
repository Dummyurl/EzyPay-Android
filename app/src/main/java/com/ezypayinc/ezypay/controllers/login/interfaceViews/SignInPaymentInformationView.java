package com.ezypayinc.ezypay.controllers.login.interfaceViews;

import java.util.ArrayList;

public interface SignInPaymentInformationView {

    void setErrorMessage(int component, int error);
    void onNetworkError(Object error);
    void populateYearSpinner(ArrayList<String> years);
    void populateMonthSpinner(int months);
    void navigateToHome();
}
