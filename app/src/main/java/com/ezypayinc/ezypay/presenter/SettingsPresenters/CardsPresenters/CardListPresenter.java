package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;
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
        cardListView.showProgressDialog();
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
                    cardListView.hideProgressDialog();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cardListView.hideProgressDialog();
                    cardListView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            cardListView.hideProgressDialog();
            e.printStackTrace();
        }
    }

    public void deleteCard(Card card) {
        cardListView.showProgressDialog();
        CardManager manager = new CardManager();
        User user = UserSingleton.getInstance().getUser();
        manager.deleteCard(card, user, new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                cardListView.hideProgressDialog();
                getCardsByUser();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cardListView.hideProgressDialog();
                cardListView.onNetworkError(error);
            }
        });
    }

    @Override
    public void onDestroy() {
        this.cardListView = null;
    }
}
