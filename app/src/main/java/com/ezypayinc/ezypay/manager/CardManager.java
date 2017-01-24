package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.CardServiceClient;
import com.ezypayinc.ezypay.model.Card;
import com.google.gson.JsonElement;

import org.json.JSONException;

import java.util.List;

public class CardManager {

    //consuming web services
    public void saveCardInServer(Card card, Response.Listener<JsonElement> successListener, Response.ErrorListener errorListener) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        service.createCard(card, successListener, errorListener);
    }

    public Card parseSaveCardResponse(JsonElement response) {
        CardServiceClient service = new CardServiceClient();
        return service.parseSaveCardResponse(response);
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

}
