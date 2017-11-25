package com.ezypayinc.ezypay.controllers.Helpers;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.card.payment.CardType;

public class CreditCardValidator {

    ArrayList<String> listOfPattern=new ArrayList<String>();

    public CreditCardValidator() {
        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
        listOfPattern.add(ptDinClb);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
        listOfPattern.add(ptJcb);
    }

    public boolean isCreditCardValid(String creditCard) {
        for (String format : listOfPattern) {
            if(creditCard.matches(format)) {
                return true;
            }
        }
        return  false;
    }

    public boolean isExpDateValid(String expDate) {
        if(expDate.length() == 7) {
            int year = Integer.valueOf(expDate.substring(3,7));
            int currentYear =  Calendar.getInstance().get(Calendar.YEAR);
            return year >= currentYear && year <= (currentYear + 10);
        }
        return  false;
    }

    public int getCardType(CardType cardType) {
        switch (cardType) {
            case VISA:
                return 1;
            case MASTERCARD:
                return 2;
            case AMEX:
                return 3;
            case JCB:
                return 4;
            case DISCOVER:
                return 5;
            default:
                return 0;
        }
    }


}
