package com.ezypayinc.ezypay.model;

public class CommerceHistory {

    private String name;
    private String lastName;
    private String cost;
    private String code;
    private String EName;
    private String ELastName;
    private String paymentDate;

    private static final String EMPTY_STRING = " ";

    public CommerceHistory() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEName() {
        return EName;
    }

    public void setEName (String EName) {
        this.EName = EName;
    }

    public String getELastName() {
        return ELastName;
    }

    public void setELastName (String ELastName) {
        this.ELastName = ELastName;
    }


    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getEmployeeFullName() {
        if(EName != null || ELastName != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(EName).
                    append(EMPTY_STRING).
                    append(ELastName);
            return builder.toString();
        }
        return null;
    }

    public String getCustomerFullName() {
        if(name != null || lastName != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(name).
                    append(EMPTY_STRING).
                    append(lastName);
            return builder.toString();
        }
        return null;
    }
}
