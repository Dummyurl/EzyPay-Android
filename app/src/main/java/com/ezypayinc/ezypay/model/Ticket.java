package com.ezypayinc.ezypay.model;

import io.realm.RealmObject;

/**
 * Created by gustavoquesada on 12/16/16.
 */

public class Ticket extends RealmObject {

    private int ticketId;
    private int restaurantId;
    private int tableId;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

}
