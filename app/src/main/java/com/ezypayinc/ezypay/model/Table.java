package com.ezypayinc.ezypay.model;

/**
 * Created by gustavoquesada on 10/22/17.
 */

public class Table {

    private boolean isActive;
    private int tableId;
    private int tableNumber;

    public Table() {}

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}
