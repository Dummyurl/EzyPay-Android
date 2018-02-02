package com.ezypayinc.ezypay.presenter.CommercePresenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IPaymentDetailView;
import com.ezypayinc.ezypay.manager.CurrencyManager;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.Currency;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

import java.util.List;

public class PaymentDetailPresenter implements IPaymentDetailPresenter {

    private IPaymentDetailView mView;
    private List<Currency> mCurrencyList;

    public PaymentDetailPresenter(IPaymentDetailView view) {
        mView = view;
    }

    @Override
    public void getCurrencies() {
        mView.showProgressDialog();
        final CurrencyManager manager = new CurrencyManager();
        User user = UserSingleton.getInstance().getUser();
        manager.getAllCurrencies(user.getToken(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                mCurrencyList = manager.parseCurrencyList(response);
                mView.dismissProgressDialog();
                populateCurrency();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.dismissProgressDialog();
            }
        });
    }

    @Override
    public void savePaymentCost(float cost, int tableNumber, int currencyIndex) {
        User user = UserSingleton.getInstance().getUser();
        Currency currency = mCurrencyList.get(currencyIndex);
        Payment payment = new Payment();
        payment.setTableNumber(tableNumber);
        payment.setCurrency(currency);
        payment.setCost(cost);
        if(user.getUserType() == 4) {
            payment.setEmployeeId(user.getId());
        } else  {
            payment.setEmployeeId(0);
        }
        mView.navigateToQRFragment(payment);
    }

    @Override
    public void sendBillNotification(final float cost, int currencyIndex, final Payment payment) {
        mView.showProgressDialog();
        final User user = UserSingleton.getInstance().getUser();
        final Currency currency = mCurrencyList.get(currencyIndex);
        PaymentManager manager = new PaymentManager();
        try {
            manager.updatePaymentAmount(payment.getId(), currency.getId(), cost, user.getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    sendNotification(cost, currency, payment, user);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.dismissProgressDialog();
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            mView.dismissProgressDialog();
        }
    }

    private void sendNotification(float cost, Currency currency, Payment payment, User user) {
        PushNotificationsManager manager = new PushNotificationsManager();
        try {
            manager.sendBillNotification(payment.getUserId(), currency.getCode(), cost, payment.getId(),
                    user.getToken(), new Response.Listener<JsonElement>() {
                        @Override
                        public void onResponse(JsonElement response) {
                            mView.dismissProgressDialog();
                            mView.navigateToPreviousView();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mView.dismissProgressDialog();
                            mView.onNetworkError(error);
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
            mView.dismissProgressDialog();
        }
    }

    private void populateCurrency() {
        String [] data = new String[mCurrencyList.size()];
        for (int i = 0; i < mCurrencyList.size(); i++) {
            data[i] = getCurrencySymbol(mCurrencyList.get(i).getCode());
        }
        mView.loadCurrencies(data);
    }

    private String getCurrencySymbol(String code) {
        java.util.Currency currency = java.util.Currency.getInstance(code);
        return currency.getSymbol();
    }
}
