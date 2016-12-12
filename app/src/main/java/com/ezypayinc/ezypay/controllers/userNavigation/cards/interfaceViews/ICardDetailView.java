package com.ezypayinc.ezypay.controllers.userNavigation.cards.interfaceViews;

/**
 * Created by Gustavo Quesada S on 11/12/2016.
 */

public interface ICardDetailView {
    void setError(int component, int errorMessage);
    void onNetworkError(Object error);
    void navigateToCardList();
    void showProgressDialog();
    void hideProgressDialog();
}
