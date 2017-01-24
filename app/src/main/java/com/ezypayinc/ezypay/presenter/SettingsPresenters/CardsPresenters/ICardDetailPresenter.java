package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import com.ezypayinc.ezypay.model.Card;

/**
 * Created by Gustavo Quesada S on 11/12/2016.
 */

public interface ICardDetailPresenter {

    boolean validateFields(String number, String cvv, String month, String year);
    void insertCard(Card card);
    void updateCard(Card card);
    void onDestroy();
}
