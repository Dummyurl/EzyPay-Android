package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import android.os.Debug;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ScannerView;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

public class ScannerPresenter implements IScannerPresenter {
    private ScannerView view;

    public ScannerPresenter(ScannerView view) {
        this.view = view;
    }

    @Override
    public void validatePayment() {
        final PaymentManager manager = new PaymentManager();
        manager.deletePayment();
        manager.getActivePaymentByUser(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                Payment payment = manager.parsePayment(response);
                if(payment != null) {
                    manager.addPayment(payment);
                    view.showRestaurantDetail(new Payment());
                } else {
                    view.showScannerView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.onNetworkError(error);
            }
        });
    }

    @Override
    public void addPayment(String qrString) {
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonObject = jsonParser.parse(qrString).getAsJsonObject();
        PaymentManager manager = new PaymentManager();
        final Payment payment = manager.parsePayment(jsonObject);
        manager.deletePayment();
        manager.addPayment(payment);
        try {
            manager.createPayment(payment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    view.showRestaurantDetail(payment);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException ex) {
            Log.e("JSONException", ex.getMessage());
        }

    }

    @Override
    public void deletePayment() {
        PaymentManager manager = new PaymentManager();
        manager.deletePayment();
        view.showScannerView();
    }

    @Override
    public void onDestroy() {

    }
}
