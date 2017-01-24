package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.TicketServiceClient;
import com.ezypayinc.ezypay.database.TicketData;
import com.ezypayinc.ezypay.model.Ticket;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class TicketManager {

    //database methods
    public void saveTicket(Ticket ticket) {
        TicketData data = new TicketData();
        data.saveTicket(ticket);
    }

    public Ticket getTicket() {
        TicketData data = new TicketData();
        return data.getTicket();
    }

    public void deleteTicket() {
        TicketData data = new TicketData();
        data.deleteTicket();
    }

    //web services methods
    public void createTicket(Ticket ticket, Response.Listener<JsonElement>  successHandler, Response.ErrorListener errorListener) throws JSONException {
        TicketServiceClient service = new TicketServiceClient();
        service.createTicket(ticket, successHandler, errorListener  );
    }
}
