package com.ezypayinc.ezypay.connection;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gustavoquesada on 11/6/16.
 */

public class CardServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "card";

    public CardServiceClient(Context context) {
        connectionManager = new ConnectionManager(context);
    }

    public void createCard(Card card, Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("idUser", card.mUser.mId);
        parameters.put("cardnumber", card.mCardNumber);
        parameters.put("cvv", card.mCvv);
        parameters.put("month", card.month);
        parameters.put("year", card.year);
        int httpMethod = Request.Method.POST;
        connectionManager.sendRequest(httpMethod,BASE_URL,parameters,successHandler, errorListener);
    }
}