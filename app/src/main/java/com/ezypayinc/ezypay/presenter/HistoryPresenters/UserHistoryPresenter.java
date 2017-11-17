package com.ezypayinc.ezypay.presenter.HistoryPresenters;


import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.history.interfaceViews.IHistoryView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.model.UserHistory;
import com.google.gson.JsonElement;

import java.util.List;

public class UserHistoryPresenter implements IUserHistoryPresenter {

    private IHistoryView mView;

    public UserHistoryPresenter(IHistoryView view) {
        mView = view;
    }


    @Override
    public void getUserHistory() {
        mView.showProgressDialog();
        final UserManager manager = new UserManager();
        manager.getUserHistory(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                List<UserHistory> userHistoryList = manager.parseUserHistory(response);
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

    private void getHistoryDates(final List<UserHistory> userHistoryList) {
        final UserManager manager = new UserManager();
        manager.getUserHistoryDates(UserSingleton.getInstance().getUser(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                List<HistoryDate> dates = manager.parseUserHistoryDates(response);
                mView.showHistoryRecords(userHistoryList, dates);
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
        mView = null;
    }
}
