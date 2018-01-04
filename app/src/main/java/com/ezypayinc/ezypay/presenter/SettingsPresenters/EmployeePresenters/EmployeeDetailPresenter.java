package com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
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
    public void insertEmployee(User user) {
        if(validateFields(user)) {
            mView.showProgressDialog();
            User currentUser = UserSingleton.getInstance().getUser();
            user.setEmployeeBoss(currentUser);
            UserManager manager = new UserManager();
            try {
                manager.registerEmployee(user, new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        mView.dismissProgressDialog();
                        mView.goToEmployeeList();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.onNetworkError(error);
                        mView.dismissProgressDialog();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                mView.dismissProgressDialog();
            }
        }

    }

    @Override
    public void updateEmployee(User user) {
        if(validateFields(user)) {
            mView.showProgressDialog();
            User currentUser = UserSingleton.getInstance().getUser();
            UserManager manager = new UserManager();
            try {
                manager.updateEmployee(user, currentUser.getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        mView.dismissProgressDialog();
                        mView.goToEmployeeList();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.dismissProgressDialog();
                        mView.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                mView.dismissProgressDialog();
                e.printStackTrace();
            }
        }
    }

    private boolean validateFields(User user) {
        if(user != null) {
            if (TextUtils.isEmpty(user.getName())) {
                mView.errorNameRequired();
                return false;
            }

            if (TextUtils.isEmpty(user.getLastName())) {
                mView.errorLastNameRequired();
                return false;
            }

            if (TextUtils.isEmpty(user.getEmail())) {
                mView.errorUserNameRequired();
                return false;
            }

            if (TextUtils.isEmpty(user.getPassword())) {
                mView.errorPasswordRequired();
                return false;
            }

            return true;
        }
        return false;
    }
}
