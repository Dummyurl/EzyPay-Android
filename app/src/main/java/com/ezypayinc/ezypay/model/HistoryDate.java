package com.ezypayinc.ezypay.model;

public class HistoryDate {

    private  int quantity;
    private String paymentDate;

    public HistoryDate() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
