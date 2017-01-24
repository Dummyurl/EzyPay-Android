package com.ezypayinc.ezypay.presenter.SettingsPresenters;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditUserView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

public class EditUserPresenter implements IEditUserPresenter {

    private IEditUserView mView;

    public EditUserPresenter(IEditUserView view) {
        mView = view;
    }


    @Override
    public void updateUser(String name, String lastName, String email, String phoneNumber) {
        if (validateFields(name, lastName, phoneNumber, email)) {
            final UserManager manager = new UserManager();
            final User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            mView.showProgressDialog();
            try {
                manager.updateUser(user, new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        mView.hideProgressDialog();
                        updateLocalUser(user, manager);
                        mView.navigateToSettingsView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.hideProgressDialog();
                        mView.onNetworkError(error);
                    }
                });
            } catch (JSONException e) {
                mView.hideProgressDialog();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    private boolean validateFields(String username, String lastName, String phoneNumber, String email) {
        if (TextUtils.isEmpty(username)) {
            mView.setErrorMessage(R.id.name_editText_edit_user_fragment, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(lastName)) {
            mView.setErrorMessage(R.id.lastName_editText_edit_user_fragment, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(phoneNumber)) {
            mView.setErrorMessage(R.id.phoneNumber_editText_edit_user_fragment, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(email)) {
            mView.setErrorMessage(R.id.email_editText_edit_user_fragment, R.string.error_field_required);
            return false;
        }

        if(!validateEmail(email)){
            mView.setErrorMessage(R.id.email_editText_edit_user_fragment, R.string.error_field_required);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    private void updateLocalUser(User userToUpdate, UserManager manager) {
        userToUpdate.setId(UserSingleton.getInstance().getUser().getId());
        userToUpdate.setToken(UserSingleton.getInstance().getUser().getToken());
        manager.updateLocalUser(userToUpdate);
        UserSingleton.getInstance().setUser(userToUpdate);
    }
}
