package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.IScannerView;
import com.ezypayinc.ezypay.manager.PaymentManager;
import com.ezypayinc.ezypay.manager.PushNotificationsManager;
import com.ezypayinc.ezypay.model.Currency;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class ScannerPresenter implements IScannerPresenter {
    private IScannerView view;
    private Payment activePayment;

    public ScannerPresenter(IScannerView view) {
        this.view = view;
    }

    @Override
    public void validatePayment() {
        final PaymentManager manager = new PaymentManager();
        view.showProgressDialog();
        manager.deletePayment();
        manager.getActivePaymentByUser(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                Payment payment = manager.parsePayment(response);
                view.dismissProgressDialog();
                if(payment != null) {
                    activePayment = payment;
                    manager.addPayment(payment);
                    if (payment.getFriends() != null && payment.getFriends().size() > 0) {
                        view.goToContactsList(payment);
                    } else {
                        view.showRestaurantDetail(payment);
                    }
                } else {
                    view.showScannerView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.dismissProgressDialog();
                view.onNetworkError(error);
            }
        });
    }

    @Override
    public void addPayment(String qrString) {
        view.showProgressDialog();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonObject = jsonParser.parse(qrString).getAsJsonObject();
        PaymentManager manager = new PaymentManager();
        final Payment payment = manager.parsePayment(jsonObject);
        manager.deletePayment();
        manager.addPayment(payment);
        activePayment = payment;
        activePayment.setUserId(UserSingleton.getInstance().getUser().getId());
        try {
            manager.createPayment(payment, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    view.dismissProgressDialog();
                    view.showRestaurantDetail(payment);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.dismissProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException ex) {
            view.dismissProgressDialog();
            Log.e("JSONException", ex.getMessage());
        }

    }

    @Override
    public void deletePayment() {
        final PaymentManager manager = new PaymentManager();
        if(activePayment != null) {
            view.showProgressDialog();
            manager.deletePayment(activePayment.getId(), UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    manager.deletePayment();
                    activePayment = null;
                    view.dismissProgressDialog();
                    view.showScannerView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.dismissProgressDialog();
                    view.onNetworkError(error);
                }
            });
        }
    }

    @Override
    public void loadCommerceImage(Context context, ImageView imageView) {
        if (activePayment != null && activePayment.getCommerce() != null) {
            Picasso.with(context).load(activePayment.getCommerce().getAvatar()).into(imageView);
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
                        Log.d("Bill Request Response", response.toString());
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
        PaymentManager manager = new PaymentManager();

        if(activePayment != null) {
            User commerce = activePayment.getCommerce();
            Currency currency = activePayment.getCurrency();
            activePayment = manager.getPayment();
            view.showProgressDialog();
            activePayment = manager.updatePaymentDate(activePayment, EzyPayApplication.getInstance().getCurrentDate());
            final Payment payment = new Payment(activePayment);
            payment.setCommerce(commerce);
            payment.setCurrency(currency);
            try {
                manager.updatePayment(activePayment, UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        view.dismissProgressDialog();
                        view.goToContactsList(payment);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.dismissProgressDialog();
                        view.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                view.dismissProgressDialog();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
