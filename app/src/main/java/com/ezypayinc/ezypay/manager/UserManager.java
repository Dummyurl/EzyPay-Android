package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.SessionServiceClient;
import com.ezypayinc.ezypay.connection.UserServiceClient;
import com.ezypayinc.ezypay.database.UserData;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by gustavoquesada on 11/22/16.
 */

public class UserManager {
    /*Database methods*/
    public User getUser() {
        UserData userData = new UserData();
        return userData.getUser();
    }

    public void addUser(User user) {
        UserData userData = new UserData();
        userData.addUser(user);
    }

    public void deleteUser() {
        UserData userData = new UserData();
        userData.deleteUser();
    }

    public void addCards(List<Card> cards) {
        UserData userData = new UserData();
        userData.addCards(cards);
    }

    public Card getCardById(int cardId) {
        UserData userData = new UserData();
        return userData.getCardById(cardId);
    }

    /*web services methods*/
    public void loginMethod(String email, String password, Response.Listener successListener, Response.ErrorListener errorListener) throws JSONException {
        SessionServiceClient service = new SessionServiceClient();
        service.loginMethod(email, password,successListener, errorListener);
    }

    public User parseLoginResponse(JSONObject response) throws JSONException {
        SessionServiceClient service = new SessionServiceClient();
        return service.parseLoginMethod(response);
    }

    public void getUserByIdFromServer(int userId, Response.Listener successListener, Response.ErrorListener errorListener) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.getUserById(userId, successListener, errorListener);
    }

    public User parseUserFromServer(JSONObject response) {
         UserServiceClient service = new UserServiceClient();
         return  service.parseUserFromServer(response);
    }

    public void saveUserInServer(User user, Response.Listener successListener, Response.ErrorListener errorListener) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.registerUser(user, successListener, errorListener);
    }

    public int parseRegisterUser(JSONObject response) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        return service.parseRegisterUser(response);
    }
}
