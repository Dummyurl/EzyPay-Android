package com.ezypayinc.ezypay.presenter.CommercePresenter;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews.ICommerceTableListView;
import com.ezypayinc.ezypay.manager.TableManager;
import com.ezypayinc.ezypay.model.Table;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import java.util.List;

public class CommerceTableListPresenter implements ICommerceTableListPresenter {

    private ICommerceTableListView mView;

    public CommerceTableListPresenter(ICommerceTableListView view) {
        mView = view;
    }

    public void getTables() {
        mView.showProgressDialog();
        final TableManager manager = new TableManager();
        User currentUser = UserSingleton.getInstance().getUser();
        int restaurantId = currentUser.getUserType() == 4 ? currentUser.getBoss().getId() : currentUser.getId();
        manager.getTablesByRestaurant(restaurantId, currentUser.getToken(), new Response.Listener<JsonElement>() {
            @Override
            public void onResponse(JsonElement response) {
                if(response != null) {
                    List<Table> tableList = manager.parseTableList(response);
                    mView.showTables(tableList);
                }
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
}
