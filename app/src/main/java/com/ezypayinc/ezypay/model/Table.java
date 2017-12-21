package com.ezypayinc.ezypay.model;


public class Table {

    private int isActive;
    private int tableId;
    private int tableNumber;

    public Table() {}

    public int isActive() {
        return isActive;
    }

    public void setActive(int active) {
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
