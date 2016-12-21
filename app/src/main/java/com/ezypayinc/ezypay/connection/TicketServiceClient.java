package com.ezypayinc.ezypay.connection;

import com.android.volley.Request;
import com.android.volley.Response;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.model.Ticket;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by gustavoquesada on 12/18/16.
 */

public class TicketServiceClient {

    private ConnectionManager connectionManager;
    private static final String BASE_URL  = "ticket/";
    private static final String CONTENT_TYPE = "application/json";

    public TicketServiceClient() {
        connectionManager = new ConnectionManager();
    }

    public void createTicket(Ticket ticket, Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        User user = UserSingleton.getInstance().getUser();
        //set headers
        String oauthToken = "Bearer "+ user.getToken();
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", oauthToken);
        headers.put("Content-Type", CONTENT_TYPE);
        //set parameters
        JSONObject parameters = new JSONObject();
        parameters.put("restaurantId", ticket.getRestaurantId());
        parameters.put("tableId", ticket.getTableId());
        int httpMethod = Request.Method.POST;
        connectionManager.sendRequest(httpMethod,BASE_URL,parameters,headers,successHandler, errorListener);
    }

}
