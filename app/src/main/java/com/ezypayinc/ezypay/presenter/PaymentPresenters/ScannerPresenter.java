package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ScannerView;
import com.ezypayinc.ezypay.manager.TicketManager;
import com.ezypayinc.ezypay.model.Ticket;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

public class ScannerPresenter implements IScannerPresenter {
    private ScannerView view;

    public ScannerPresenter(ScannerView view) {
        this.view = view;
    }

    @Override
    public void validateTicket() {
        TicketManager manager = new TicketManager();
        Ticket ticket = manager.getTicket();
        if (ticket == null) {
            view.showScannerView();
        } else {
            view.showRestaurantDetail(ticket);
        }
    }

    @Override
    public void addTicket(String qrString) {
        try {
            JSONObject jsonObject = new JSONObject(qrString);
            final Ticket ticket = new Ticket();
            ticket.setTicketId(jsonObject.getInt("ticketId"));
            ticket.setRestaurantId(jsonObject.getInt("restaurantId"));
            ticket.setTableId(jsonObject.getInt("tableId"));
            TicketManager manager = new TicketManager();
            manager.deleteTicket();
            manager.saveTicket(ticket);
            manager.createTicket(ticket, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    view.showRestaurantDetail(ticket);
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

    @Override
    public void deleteTicket() {
        TicketManager manager = new TicketManager();
        manager.deleteTicket();
        view.showScannerView();
    }

    @Override
    public void onDestroy() {

    }
}
