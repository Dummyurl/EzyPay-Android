package com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews.IEmployeeDetailView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class EmployeeDetailPresenter implements IEmployeeDetailPresenter {

    private IEmployeeDetailView mView;

    public EmployeeDetailPresenter(IEmployeeDetailView view) {
        mView = view;
    }

    @Override
    public void getEmployeeDetail(int employeeId) {
        final UserManager manager = new UserManager();
        mView.showProgressDialog();
        try {
            manager.getUserByIdFromServer(employeeId, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    User user = manager.parseUserFromServer(response);
                    mView.setupEmployee(user);
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

    @Override
    public void saveEmployee(User user) {

    }
}
