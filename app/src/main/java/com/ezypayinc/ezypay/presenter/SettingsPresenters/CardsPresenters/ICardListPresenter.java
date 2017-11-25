package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import com.ezypayinc.ezypay.model.Card;

public interface ICardListPresenter {
    void getCardsByUser();
    void deleteCard(Card card);
    void onDestroy();
}
