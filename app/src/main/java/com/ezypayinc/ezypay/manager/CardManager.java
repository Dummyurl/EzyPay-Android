package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.CardServiceClient;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

import java.util.List;

public class CardManager {

    //consuming web services
    public void saveCardInServer(Card card, Response.Listener<JsonElement> successListener, Response.ErrorListener errorListener) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        service.createCard(card, successListener, errorListener);
    }

    public void getCardsByUser(Response.Listener<JsonElement> successListener, Response.ErrorListener errorListener) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        service.getCardsByUser(successListener, errorListener);
    }

    public List<Card> parseGetCardsResponse(JsonElement response) {
        CardServiceClient service = new CardServiceClient();
        return service.parseGetCardsResponse(response);
    }

    public void updateCardInServer(Card card, Response.Listener<JsonElement> successListener, Response.ErrorListener errorListener) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        service.updateCard(card, successListener, errorListener);
    }

    public void deleteCard(Card card, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener errorListener) {
        CardServiceClient service = new CardServiceClient();
        service.deleteCard(card, user, successHandler, errorListener);
    }
}
