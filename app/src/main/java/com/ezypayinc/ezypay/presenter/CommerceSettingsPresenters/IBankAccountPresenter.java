package com.ezypayinc.ezypay.presenter.CommerceSettingsPresenters;


import com.ezypayinc.ezypay.model.BankAccount;

public interface IBankAccountPresenter {
    void getBankAccount();
    void insertBankAccount(BankAccount bankAccount);
    void updateBankAccount(BankAccount bankAccount);
}
