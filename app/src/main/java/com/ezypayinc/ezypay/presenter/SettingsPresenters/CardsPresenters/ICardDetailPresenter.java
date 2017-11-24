package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import com.ezypayinc.ezypay.model.Card;

public interface ICardDetailPresenter {

    boolean validateFields(String number, String ccv, String expDate);
    void insertCard(Card card);
    void updateCard(Card card);
    void onDestroy();
}
