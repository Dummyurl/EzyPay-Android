package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.model.Table;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TableServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  =  "table/";
    private static final String CONTENT_TYPE = "application/json";

    public TableServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void getTablesByRestaurant(int restaurantId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        String oauthToken = "Bearer "+ token;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        String url = BASE_URL + "restaurant/" + String.valueOf(restaurantId);

        connectionManager.sendCustomRequest(Request.Method.GET, url, null, headers, successHandler, failureHandler);
    }

    public List<Table> parseTableList(JsonElement response) {
        Gson gson = new Gson();
        Table[] tablesArray = gson.fromJson(response, Table[].class);
        List<Table> tableList = Arrays.asList(tablesArray);
        return tableList;
    }
}
