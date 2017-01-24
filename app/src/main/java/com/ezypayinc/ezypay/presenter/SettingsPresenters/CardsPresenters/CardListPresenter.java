package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.google.gson.JsonElement;
import org.json.JSONException;

import java.util.List;

public class CardListPresenter implements ICardListPresenter {
    private ICardListView cardListView;

    public CardListPresenter(ICardListView cardListView) {
        this.cardListView = cardListView;
    }

    @Override
    public void getCardsByUser() {
        final CardManager cardManager = new CardManager();
        final UserManager userManager = new UserManager();
        try {
            cardManager.getCardsByUser(new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    List<Card> cardList;
                    cardList = cardManager.parseGetCardsResponse(response);
                    cardListView.populateCardList(cardList);
                    userManager.addCards(cardList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cardListView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            cardListView.hideProgressDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        this.cardListView = null;
    }
}
