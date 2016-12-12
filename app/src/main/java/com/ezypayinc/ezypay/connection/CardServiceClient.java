package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gustavoquesada on 11/6/16.
 */

public class CardServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "card/";
    private static final String CONTENT_TYPE = "application/json";

    public CardServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void createCard(Card card, Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("number", card.getNumber());
        parameters.put("cvv", card.getCvv());
        parameters.put("month", card.getMonth());
        parameters.put("year", card.getYear());
        parameters.put("userId", user.getId());
        int httpMethod = Request.Method.POST;
        connectionManager.sendRequest(httpMethod,BASE_URL,parameters,headers,successHandler, errorListener);
    }

    public Card parseSaveCardResponse(JSONObject response) throws JSONException {
        Card card = new Card();
        card.setNumber(response.getString("number"));
        card.setCvv(response.getInt("cvv"));
        card.setMonth(response.getInt("month"));
        card.setYear(response.getInt("year"));
        return card;
    }

    public void getCardsByUser(Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("userId", user.getId());
        JSONArray array = new JSONArray();
        array.put(parameters);
        int httpMethod = Request.Method.POST;
        String url = BASE_URL + "getAll";
        connectionManager.sendArrayRequest(httpMethod,url,array,headers, successHandler, errorListener);
    }

    public List<Card> parseGetCardsResponse(JSONArray response) throws JSONException {
        List<Card> cardList = new ArrayList<>();
        for(int n = 0; n < response.length(); n++) {
            JSONObject object = response.getJSONObject(n);
            Card card = new Card();
            card.setId(object.getInt("id"));
            card.setNumber(object.getString("number"));
            card.setCvv(object.getInt("cvv"));
            card.setMonth(object.getInt("month"));
            card.setYear(object.getInt("year"));
            cardList.add(card);
        }
        return cardList;
    }

    public void updateCard(Card card, Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("number", card.getNumber());
        parameters.put("cvv", card.getCvv());
        parameters.put("month", card.getMonth());
        parameters.put("year", card.getYear());
        parameters.put("userId", user.getId());
        JSONArray array = new JSONArray();
        array.put(parameters);
        int httpMethod = Request.Method.PUT;
        String url = BASE_URL + card.getId();
        connectionManager.sendArrayRequest(httpMethod,url,array,headers,successHandler, errorListener);
    }
}
