package com.ezypayinc.ezypay.presenter.LoginPresenters;

import android.text.TextUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.LoginView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gustavoquesada on 11/23/16.
 */

public class LoginPresenter  implements ILoginPresenter{

    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    /*interface methods*/
    @Override
    public void loginMethod(String email, String password) {
        if(validateEmail(email)) {
            if(validatePassword(password)) {
                login(email, password);
            }
        }

    }

    @Override
    public void validateUser() {
        UserManager manager = new UserManager();
        User user = manager.getUser();
        if(user != null && user.getToken() != null) {
            UserSingleton userSingleton = UserSingleton.getInstance();
            userSingleton.setUser(user);
            loginView.navigateToHome();
        }
    }

    @Override
    public void onDestroy() {
        this.loginView = null;
    }


    /*Actions methods*/
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            loginView.setPasswordError(R.string.error_invalid_password);
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            loginView.setUsernameError(R.string.error_field_required);
            return false;
        }

        if(!email.contains("@")) {
            loginView.setUsernameError(R.string.error_invalid_email);
            return false;
        }
        return true;
    }


    private void login(String email, String password) {
        final UserManager manager = new UserManager();
        loginView.showProgressDialog();

        try {
            manager.loginMethod(email, password, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User user = manager.parseLoginResponse(response);
                        getUserFromServer(user);
                    } catch (JSONException e) {
                        loginView.hideProgressDialog();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loginView.hideProgressDialog();
                    loginView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            loginView.hideProgressDialog();
            e.printStackTrace();
        }
    }

    public void getUserFromServer(final User user) {
        final UserSingleton userSingleton = UserSingleton.getInstance();
        userSingleton.setUser(user);
        final UserManager manager = new UserManager();
        try {
            manager.getUserByIdFromServer(user.getId(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    User userFromServer = manager.parseUserFromServer(response);
                    userFromServer.setToken(user.getToken());
                    userSingleton.setUser(userFromServer);
                    manager.addUser(user);
                    loginView.hideProgressDialog();
                    loginView.navigateToHome();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loginView.hideProgressDialog();
                    loginView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            loginView.hideProgressDialog();
            e.printStackTrace();
        }

    }
}
