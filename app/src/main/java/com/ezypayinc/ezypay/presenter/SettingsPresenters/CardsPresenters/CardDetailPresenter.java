package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.CreditCardValidator;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardDetailView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.model.Card;
import com.google.gson.JsonElement;

import org.json.JSONException;

import io.card.payment.CardType;
import io.card.payment.CreditCard;


public class CardDetailPresenter implements ICardDetailPresenter {
    private ICardDetailView view;

    public CardDetailPresenter(ICardDetailView view) {
        this.view = view;
    }

    @Override
    public boolean validateFields(String number, String ccv, String expDate, CardType cardType) {
        CreditCardValidator validator = new CreditCardValidator();
        if (TextUtils.isEmpty(number)) {
            view.setError(R.id.card_detail_card_number, R.string.error_field_required);
            return false;
        }
        if (TextUtils.isEmpty(expDate)) {
            view.setError(R.id.card_detail_exp_date, R.string.error_field_required);
            return false;
        }
        if (TextUtils.isEmpty(ccv)) {
            view.setError(R.id.card_detail_cvv, R.string.error_field_required);
            return false;
        }
        if (!validator.isCreditCardValid(number)) {
            view.setError(R.id.card_detail_card_number, R.string.error_invalid_card_number);
            return false;
        }
        if (!validator.isExpDateValid(expDate)) {
            view.setError(R.id.card_detail_exp_date, R.string.error_invalid_exp_date_number);
            return false;
        }
        if(validator.getCardType(cardType) == 0) {
            view.setError(R.id.card_detail_card_number, R.string.error_invalid_card_type);
            return false;
        }

        return true;
    }

    public boolean validateFields(String ccv, String expDate) {
        CreditCardValidator validator = new CreditCardValidator();
        if (TextUtils.isEmpty(expDate)) {
            view.setError(R.id.card_detail_exp_date, R.string.error_field_required);
            return false;
        }
        if (TextUtils.isEmpty(ccv)) {
            view.setError(R.id.card_detail_cvv, R.string.error_field_required);
            return false;
        }
        if (!validator.isExpDateValid(expDate)) {
            view.setError(R.id.card_detail_exp_date, R.string.error_invalid_exp_date_number);
            return false;
        }
        return true;
    }

    @Override
    public void insertCard(Card card) {
        view.showProgressDialog();
        final CardManager cardManager = new CardManager();
        try {
            cardManager.saveCardInServer(card, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    view.hideProgressDialog();
                    view.navigateToCardList();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCard(Card card) {
        view.showProgressDialog();
        CardManager manager = new CardManager();
        try {
            manager.updateCardInServer(card, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    view.hideProgressDialog();
                    view.navigateToCardList();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }
}
