package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import android.os.Debug;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.connection.PushNotificationsServiceClient;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ScannerView;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.Payment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;

import java.util.Date;

public class ScannerPresenter implements IScannerPresenter {
    private ScannerView view;
    private Payment activePayment;

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
                    activePayment = payment;
                    manager.addPayment(payment);
                    view.showRestaurantDetail(payment);
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
        activePayment = payment;
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
        final PaymentManager manager = new PaymentManager();
        if(activePayment != null) {
            manager.deletePayment(activePayment.getId(), UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    manager.deletePayment();
                    activePayment = null;
                    view.showScannerView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.onNetworkError(error);
                }
            });
        }
    }

    public void callWaiter() {
        PushNotificationsManager manager = new PushNotificationsManager();
        if(activePayment != null) {
            try {
                manager.callWaiterNotification(activePayment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        Log.e("Call Waiter Response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendBillRequest() {
        PushNotificationsManager manager = new PushNotificationsManager();
        if(activePayment != null) {
            try {
                manager.sendBillRequestNotification(activePayment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        Log.e("Bill Request Response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void splitPayment() {
        if(activePayment != null) {
            activePayment.setPaymentDate(new Date());
            PaymentManager manager = new PaymentManager();
            try {
                manager.updatePayment(activePayment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        view.goToContactsList();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
