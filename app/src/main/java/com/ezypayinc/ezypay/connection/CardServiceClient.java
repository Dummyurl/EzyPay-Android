package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CardServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "card/";
    private static final String CONTENT_TYPE = "application/json";

    public CardServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void createCard(Card card, Response.Listener<JsonElement> successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        String cardHolderName = user.getName() + " " + user.getLastName();
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("cardNumber", card.getCardNumber());
        parameters.put("cardHolderName", cardHolderName);
        parameters.put("customerId", user.getCustomerId());
        parameters.put("isFavorite", card.isFavorite());
        parameters.put("ccv", card.getCcv());
        parameters.put("expirationDate", card.getExpirationDate());
        parameters.put("userId", user.getId());
        parameters.put("cardVendor", card.getCardVendor());
        int httpMethod = Request.Method.POST;
        connectionManager.sendCustomRequest(httpMethod,BASE_URL,parameters,headers,successHandler, errorListener);
    }

    public Card parseSaveCardResponse(JsonElement response) {
        Card card = new Card();
        JsonObject object = response.getAsJsonObject();
        /*card.setNumber(object.get("number").getAsString());
        card.setCcv(object.get("cvv").getAsInt());
        card.setMonth(object.get("month").getAsInt());
        card.setYear(object.get("year").getAsInt());*/
        return card;
    }

    public void getCardsByUser(Response.Listener<JsonElement> successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("userId", user.getId());
        int httpMethod = Request.Method.POST;
        String url = BASE_URL + "getAll";
        connectionManager.sendCustomRequest(httpMethod,url,parameters,headers, successHandler, errorListener);
    }

    public List<Card> parseGetCardsResponse(JsonElement response) {
        Gson gson = new Gson();
        Card[] cardArray = gson.fromJson(response,Card[].class);
        List<Card> cardList = Arrays.asList(cardArray);
        return cardList;
    }

    public void updateCard(Card card, Response.Listener<JsonElement> successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        /*parameters.put("number", card.getNumber());
        parameters.put("cvv", card.getCcv());
        parameters.put("month", card.getMonth());
        parameters.put("year", card.getYear());
        parameters.put("userId", user.getId());*/
        int httpMethod = Request.Method.PUT;
        String url = BASE_URL + card.getId();
        connectionManager.sendCustomRequest(httpMethod,url,parameters,headers,successHandler, errorListener);
    }
}
