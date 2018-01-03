package com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews.IEmployeeListView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

import java.util.List;

public class EmployeeListPresenter implements IEmployeeListPresenter {

    private IEmployeeListView mView;

    public EmployeeListPresenter(IEmployeeListView view) {
        mView = view;
    }

    @Override
    public void getEmployeeList() {
        mView.showProgressDialog();
        User user = UserSingleton.getInstance().getUser();
        final UserManager manager = new UserManager();
        try {
            manager.getEmployees(user, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    List<User> employeeList = manager.parseUserList(response);
                    mView.populateEmployeeList(employeeList);
                    mView.dismissProgressDialog();
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
}
