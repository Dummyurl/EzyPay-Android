package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.CardServiceClient;
import com.ezypayinc.ezypay.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gustavoquesada on 11/26/16.
 */

public class CardManager {

    //consuming web services
    public void saveCardInServer(Card card, Response.Listener successListener, Response.ErrorListener errorListener) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        service.createCard(card, successListener, errorListener);
    }

    public Card parseSaveCardResponse(JSONObject response) throws JSONException {
        CardServiceClient service = new CardServiceClient();
        return service.parseSaveCardResponse(response);
    }
}
