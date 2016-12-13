package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews;

import com.ezypayinc.ezypay.model.Card;

import java.util.List;

/**
 * Created by Gustavo Quesada S on 10/12/2016.
 */

public interface ICardListView {

    void populateCardList(List<Card> cards);
    void onNetworkError(Object error);
    void showProgressDialog();
    void hideProgressDialog();
}
