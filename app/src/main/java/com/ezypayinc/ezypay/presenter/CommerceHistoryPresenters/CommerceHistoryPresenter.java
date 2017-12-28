package com.ezypayinc.ezypay.presenter.CommerceHistoryPresenters;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.history.interfaceViews.ICommerceHistoryView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.CommerceHistory;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import java.util.List;

public class CommerceHistoryPresenter implements ICommerceHistoryPresenter {

    private ICommerceHistoryView mView;


    public CommerceHistoryPresenter(ICommerceHistoryView view) {
        mView = view;
    }

    @Override
    public void getCommerceHistory() {

        mView.showProgressDialog();
        final UserManager manager = new UserManager();
        manager.getCommerceHistory(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                List<CommerceHistory> userHistoryList = manager.parseCommerceHistory(response);
                getHistoryDates(userHistoryList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.dismissProgressDialog();
                mView.onNetworkError(error);
            }
        });
    }

    private void getHistoryDates(final List<CommerceHistory> commerceHistoryList) {
        final UserManager manager = new UserManager();
        manager.getCommerceHistoryDates(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                List<HistoryDate> dates = manager.parseUserHistoryDates(response);
                mView.showHistoryRecords(commerceHistoryList, dates);
                mView.dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mView.dismissProgressDialog();
                mView.onNetworkError(error);
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
