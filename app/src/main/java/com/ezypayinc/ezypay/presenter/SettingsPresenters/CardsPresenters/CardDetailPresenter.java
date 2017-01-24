package com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardDetailView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.model.Card;
import com.google.gson.JsonElement;

import org.json.JSONException;


public class CardDetailPresenter implements ICardDetailPresenter {
    private ICardDetailView view;

    public CardDetailPresenter(ICardDetailView view) {
        this.view = view;
    }

    @Override
    public boolean validateFields(String number, String cvv, String month, String year) {
        if (TextUtils.isEmpty(number)) {
            view.setError(R.id.card_detail_card_number, R.string.error_field_required);
            return false;
        }
        if (number.length() < 16) {
            view.setError(R.id.card_detail_card_number, R.string.error_invalid_card_number);
            return false;
        }
        if (TextUtils.isEmpty(cvv)) {
            view.setError(R.id.card_detail_cvv, R.string.error_field_required);
            return false;
        }

        if (TextUtils.isEmpty(month)) {
            view.setError(R.id.card_detail_month, R.string.error_field_required);
            return false;
        }

        if (TextUtils.isEmpty(year) || year.length() < 0) {
            view.setError(R.id.card_detail_year, R.string.error_field_required);
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
