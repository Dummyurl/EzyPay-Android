package com.ezypayinc.ezypay.base;

import com.ezypayinc.ezypay.model.User;

/**
 * Created by gustavoquesada on 11/23/16.
 */

public class UserSingleton {

    private static UserSingleton instance;
    private User user;

    public UserSingleton() {

    }

    public static UserSingleton getInstance() {
        if(instance == null){
            instance = new UserSingleton();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
