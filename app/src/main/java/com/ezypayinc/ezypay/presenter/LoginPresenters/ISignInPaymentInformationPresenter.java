package com.ezypayinc.ezypay.presenter.LoginPresenters;

import com.ezypayinc.ezypay.model.Card;
import io.card.payment.CardType;

public interface ISignInPaymentInformationPresenter {

    boolean validateFields(String number, String ccv, String expDate, CardType cardType);
    void registerRecord(Card card);
    void onDestroy();
}
