package com.ezypayinc.ezypay.database;

import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by gustavoquesada on 11/22/16.
 */

public class UserData {

    public void addUser(final User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User userRealm = realm.createObject(User.class);
        userRealm.setId(user.getId());
        userRealm.setName(user.getName());
        userRealm.setLastName(user.getLastName());
        userRealm.setPhoneNumber(user.getPhoneNumber());
        userRealm.setEmail(user.getEmail());
        userRealm.setToken(user.getToken());
        realm.commitTransaction();

    }

    public User getUser() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        realm.commitTransaction();
        return user;
    }

    public void deleteUser() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User result = realm.where(User.class).findFirst();
        if(result != null) {
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

    public Card getCardById(int cardId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Card card = realm.where(Card.class).equalTo("id", cardId).findFirst();
        realm.commitTransaction();
        return card;
    }

    public void addCards(List<Card> cards) {
        User user = getUser();
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        user.getCards().deleteAllFromRealm();
        user.getCards().addAll(cards);
        realm.commitTransaction();

    }
}
