package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.IPaymentListView;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;

import org.json.JSONException;


public class PaymentPresenter implements IPaymentPresenter {

    private IPaymentListView mView;

    public PaymentPresenter(IPaymentListView view) {
        mView = view;
    }

    @Override
    public void performPayment(Payment payment) {
        PaymentManager manager = new PaymentManager();
        try {
            manager.performPayment(payment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    mView.goToResultView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
