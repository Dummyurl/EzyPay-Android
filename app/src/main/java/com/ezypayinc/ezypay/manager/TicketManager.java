package com.ezypayinc.ezypay.manager;

import com.android.volley.Response;
import com.ezypayinc.ezypay.connection.TicketServiceClient;
import com.ezypayinc.ezypay.database.TicketData;
import com.ezypayinc.ezypay.model.Ticket;

import org.json.JSONException;

/**
 * Created by gustavoquesada on 12/16/16.
 */

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
    public void createTicket(Ticket ticket, Response.Listener successHandler, Response.ErrorListener errorListener) throws JSONException {
        TicketServiceClient service = new TicketServiceClient();
        service.createTicket(ticket, successHandler, errorListener  );
    }
}
