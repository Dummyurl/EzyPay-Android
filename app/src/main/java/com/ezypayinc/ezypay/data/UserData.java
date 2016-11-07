package com.ezypayinc.ezypay.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by gustavoquesada on 11/6/16.
 */

public class UserData {

    private static final String USER_TABLE_NAME = "User";

    private static final String ID_USER = "idUser";
    private static final String NAME = "name";
    private static final String LASTNAME = "lastname";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    public static final String USER_TABLE_SCRIPT =
            "CREATE TABLE" + USER_TABLE_NAME + " ( "+
                    ID_USER + " INTEGER, "+
                    NAME + " TEXT, "+
                    LASTNAME + " TEXT, "+
                    PHONE_NUMBER + " TEXT, "+
                    EMAIL + " TEXT, "+
                    PASSWORD + " TEXT)";

    private DataBaseHandler dataBaseHandler;
    private SQLiteDatabase database;

    public UserData(Context context) {
        dataBaseHandler = new DataBaseHandler(context);
        database = dataBaseHandler.getWritableDatabase();
    }
}
