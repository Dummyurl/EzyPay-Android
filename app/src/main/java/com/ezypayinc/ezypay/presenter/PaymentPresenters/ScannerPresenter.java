package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ScannerView;
import com.ezypayinc.ezypay.manager.TicketManager;
import com.ezypayinc.ezypay.model.Ticket;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gustavoquesada on 12/16/16.
 */

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
            Ticket ticket = new Ticket();
            ticket.setTicketId(jsonObject.getInt("ticketId"));
            ticket.setRestaurantId(jsonObject.getInt("restaurantId"));
            ticket.setTableId(jsonObject.getInt("tableId"));
            TicketManager manager = new TicketManager();
            manager.deleteTicket();
            manager.saveTicket(ticket);
            view.showRestaurantDetail(ticket);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {

    }
}
