package com.ezypayinc.ezypay.presenter.CardsPresenters;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Gustavo Quesada S on 10/12/2016.
 */

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
            cardManager.getCardsByUser(new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    List<Card> cardList = null;
                    try {
                        cardList = cardManager.parseGetCardsResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
