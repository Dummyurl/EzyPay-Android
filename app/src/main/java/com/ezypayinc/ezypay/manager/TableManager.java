package com.ezypayinc.ezypay.manager;


import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.TableServiceClient;
import com.ezypayinc.ezypay.model.Table;
import com.google.gson.JsonElement;

import java.util.List;

public class TableManager {

    public void getTablesByRestaurant(int restaurantId, String token, Response.Listener<JsonElement> successHandler, Response.ErrorListener failureHandler) {
        TableServiceClient service = new TableServiceClient();
        service.getTablesByRestaurant(restaurantId, token, successHandler, failureHandler);
    }

    public List<Table> parseTableList(JsonElement response) {
        TableServiceClient service = new TableServiceClient();
        return  service.parseTableList(response);
    }
}
