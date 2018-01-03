package com.ezypayinc.ezypay.manager;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.SessionServiceClient;
import com.ezypayinc.ezypay.connection.UserServiceClient;
import com.ezypayinc.ezypay.database.UserData;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.CommerceHistory;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.model.UserHistory;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

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

    public void updateLocalUser(User user) {
        UserData userData = new UserData();
        userData.updateUser(user);
    }

    public User updateUserPhone(User user, String phoneNumber) {
        UserData data = new UserData();
        return data.updateUserPhone(user, phoneNumber);
    }

    /*web services methods*/
    public void loginMethod(String email, String password, Response.Listener<JsonElement>  successListener, Response.ErrorListener errorListener) throws JSONException {
        SessionServiceClient service = new SessionServiceClient();
        service.loginMethod(email, password,successListener, errorListener);
    }

    public User parseLoginResponse(JsonElement response) {
        SessionServiceClient service = new SessionServiceClient();
        return service.parseLoginMethod(response);
    }

    public void getUserByIdFromServer(int userId, Response.Listener<JsonElement>  successListener, Response.ErrorListener errorListener) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.getUserById(userId, successListener, errorListener);
    }

    public User parseUserFromServer(JsonElement response) {
         UserServiceClient service = new UserServiceClient();
         return  service.parseUserFromServer(response);
    }

    public void saveUserInServer(User user, Response.Listener<JsonElement> successListener, Response.ErrorListener errorListener) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.registerUser(user, successListener, errorListener);
    }

    public User parseRegisterUser(JsonElement response, User user)  {
        UserServiceClient service = new UserServiceClient();
        return service.parseRegisterUser(response, user);
    }

    public void validatePhoneNumbers(JSONArray phoneNumbers, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.validatePhoneNumbers(phoneNumbers, successHandler,failureHandler);
    }

    public List<Friend> parseValidatePhoneNumbers(JsonElement jsonElement) {
        UserServiceClient service = new UserServiceClient();
        return service.parseValidatePhoneNumbers(jsonElement);
    }

    public void updateUser(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.updateUser(user, successHandler, failureHandler);
    }

    public void getUserHistory(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        UserServiceClient service = new UserServiceClient();
        service.getUserHistory(user, successHandler, failureHandler);
    }

    public List<UserHistory> parseUserHistory(JsonElement response) {
        UserServiceClient service = new UserServiceClient();
        return service.parseUserHistory(response);
    }

    public void getUserHistoryDates(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        UserServiceClient service = new UserServiceClient();
        service.getUserHistoryDates(user, successHandler, failureHandler);
    }

    public List<HistoryDate> parseUserHistoryDates(JsonElement response) {
        UserServiceClient service = new UserServiceClient();
        return  service.parseUserHistoryDates(response);
    }

    public void updatePassword(String newPassword, User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.updatePassword(newPassword,user, successHandler, failureHandler);
    }

    public void uploadImge(byte[] encodeImage, User user, Response.Listener<NetworkResponse> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.uploadImge(encodeImage, user, successHandler, failureHandler);
    }

    public void getPhoneCodes(Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        UserServiceClient service = new UserServiceClient();
        service.getPhoneCodes(successHandler, failureHandler);
    }

    public List<PhoneCode> parsePhoneCode(JsonElement response) {
        UserServiceClient service = new UserServiceClient();
        return service.parsePhoneCode(response);
    }

    public void  validateUserEmail(String email, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.validateUserEmail(email, successHandler, failureHandler);
    }

    public void getCommerceHistory(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        UserServiceClient service = new UserServiceClient();
        service.getCommerceHistory(user, successHandler, failureHandler);
    }

    public List<CommerceHistory> parseCommerceHistory(JsonElement response) {
        UserServiceClient service = new UserServiceClient();
        return service.parseCommerceHistory(response);
    }

    public void getCommerceHistoryDates(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        UserServiceClient service = new UserServiceClient();
        service.getCommerceHistoryDates(user, successHandler, failureHandler);
    }

    public void getEmployees(User user, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) throws JSONException {
        UserServiceClient service = new UserServiceClient();
        service.getEmployees(user, successHandler, failureHandler);
    }

    public List<User> parseUserList(JsonElement response) {
        UserServiceClient service = new UserServiceClient();
        return service.parseUserList(response);
    }
}
