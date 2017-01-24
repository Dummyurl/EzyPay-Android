package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("number", card.getNumber());
        parameters.put("cvv", card.getCvv());
        parameters.put("month", card.getMonth());
        parameters.put("year", card.getYear());
        parameters.put("userId", user.getId());
        int httpMethod = Request.Method.POST;
        connectionManager.sendCustomRequest(httpMethod,BASE_URL,parameters,headers,successHandler, errorListener);
    }

    public Card parseSaveCardResponse(JsonElement response) {
        Card card = new Card();
        JsonObject object = response.getAsJsonObject();
        card.setNumber(object.get("number").getAsString());
        card.setCvv(object.get("cvv").getAsInt());
        card.setMonth(object.get("month").getAsInt());
        card.setYear(object.get("year").getAsInt());
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
        List<Card> cardList = new ArrayList<>();
        JsonArray jsonArray = response.getAsJsonArray();
        for(int n = 0; n < jsonArray.size(); n++) {
            JsonObject object = jsonArray.get(n).getAsJsonObject();
            if(!object.get("month").isJsonNull()) {
                Card card = new Card();
                card.setId(object.get("id").getAsInt());
                card.setNumber(object.get("number").getAsString());
                card.setCvv(object.get("cvv").getAsInt());
                card.setMonth(object.get("month").getAsInt());
                card.setYear(object.get("year").getAsInt());
                cardList.add(card);
            }
        }
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
        parameters.put("number", card.getNumber());
        parameters.put("cvv", card.getCvv());
        parameters.put("month", card.getMonth());
        parameters.put("year", card.getYear());
        parameters.put("userId", user.getId());
        int httpMethod = Request.Method.PUT;
        String url = BASE_URL + card.getId();
        connectionManager.sendCustomRequest(httpMethod,url,parameters,headers,successHandler, errorListener);
    }
}
