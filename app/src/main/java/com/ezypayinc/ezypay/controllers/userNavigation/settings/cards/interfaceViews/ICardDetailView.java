package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews;

public interface ICardDetailView {
    void setError(int component, int errorMessage);
    void onNetworkError(Object error);
    void navigateToCardList();
    void showProgressDialog();
    void hideProgressDialog();
}
