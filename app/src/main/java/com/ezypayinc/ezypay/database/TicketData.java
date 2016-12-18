package com.ezypayinc.ezypay.database;

import com.ezypayinc.ezypay.model.Ticket;

import io.realm.Realm;

/**
 * Created by gustavoquesada on 12/16/16.
 */

public class TicketData {

    public void saveTicket(Ticket ticket) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Ticket ticketRealm = realm.createObject(Ticket.class);
        ticketRealm.setTicketId(ticket.getTicketId());
        ticketRealm.setRestaurantId(ticket.getRestaurantId());
        ticketRealm.setTableId(ticket.getTableId());
        realm.commitTransaction();
    }

    public Ticket getTicket() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Ticket ticket = realm.where(Ticket.class).findFirst();
        realm.commitTransaction();
        return ticket;
    }

    public void deleteTicket() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Ticket result = realm.where(Ticket.class).findFirst();
        if(result != null) {
            result.deleteFromRealm();
        }
        realm.commitTransaction();
    }

}
