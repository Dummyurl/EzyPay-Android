package com.ezypayinc.ezypay.manager;

import com.ezypayinc.ezypay.database.TicketData;
import com.ezypayinc.ezypay.model.Ticket;

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
}
