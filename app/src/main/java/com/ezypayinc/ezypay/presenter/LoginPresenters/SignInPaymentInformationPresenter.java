package com.ezypayinc.ezypay.presenter.LoginPresenters;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.Helpers.CreditCardValidator;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInPaymentInformationView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.manager.DeviceTokenManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.LocalToken;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;

import io.card.payment.CardType;
import io.realm.RealmList;

public class SignInPaymentInformationPresenter implements ISignInPaymentInformationPresenter {
    private SignInPaymentInformationView view;
    private static final String DEVICE_TOKEN_ERROR = "Error saving token";

    public SignInPaymentInformationPresenter(SignInPaymentInformationView view) {
        this.view = view;
    }

    @Override
    public void registerRecord(Card card) {
        view.showProgressDialog();
        User user = UserSingleton.getInstance().getUser();
        saveUser(user, card);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    public boolean validateFields(String number, String ccv, String expDate, CardType cardType) {
        CreditCardValidator validator = new CreditCardValidator();
        if (TextUtils.isEmpty(number)) {
            view.setErrorMessage(R.id.sign_in_card_number, R.string.error_field_required);
            return false;
        }
        if (TextUtils.isEmpty(expDate)) {
            view.setErrorMessage(R.id.sign_in_exp_date, R.string.error_field_required);
            return false;
        }
        if (TextUtils.isEmpty(ccv)) {
            view.setErrorMessage(R.id.sign_in_cvv, R.string.error_field_required);
            return false;
        }
        if (!validator.isCreditCardValid(number)) {
            view.setErrorMessage(R.id.sign_in_card_number, R.string.error_invalid_card_number);
            return false;
        }
        if (!validator.isExpDateValid(expDate)) {
            view.setErrorMessage(R.id.sign_in_exp_date, R.string.error_invalid_exp_date_number);
            return false;
        }
        if(validator.getCardType(cardType) == 0) {
            view.setErrorMessage(R.id.sign_in_card_number, R.string.error_invalid_card_type);
            return false;
        }

        return true;
    }

    private void saveUser(final User user, final Card card) {
        final UserManager userManager = new UserManager();
        try {
            userManager.saveUserInServer(user, 0, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    User newUser = userManager.parseRegisterUser(response, user);
                    login(newUser, card);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            view.hideProgressDialog();
            e.printStackTrace();
        }
    }

    private void login(final User user, final Card card) {
        String scope = user.getCredentials() != null ? user.getCredentials().getPlatform() : null;
        String platformToken = user.getCredentials() != null ? user.getCredentials().getPlatformToken() : null;
        String password = user.getCredentials() != null ? user.getCredentials().getCredential() : user.getPassword();
        final UserManager userManager = new UserManager();
        try {
            userManager.loginMethod(user.getEmail(), password, scope, platformToken, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    User userFromLogin = userManager.parseLoginResponse(response);
                    user.setToken(userFromLogin.getToken());
                    UserSingleton.getInstance().setUser(user);
                    saveCard(user, card);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            view.hideProgressDialog();
            e.printStackTrace();
        }
    }

    private void saveCard(final User user, Card card) {
        final CardManager cardManager = new CardManager();
        final UserManager userManager = new UserManager();
        try {
            cardManager.saveCardInServer(card,  new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    UserSingleton.getInstance().setUser(user);
                    userManager.deleteUser();
                    userManager.addUser(user);
                    view.hideProgressDialog();
                    view.navigateToHome();
                    registerLocalToken();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.hideProgressDialog();
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            view.hideProgressDialog();
            e.printStackTrace();
        }
    }

    private void registerLocalToken() {
        final DeviceTokenManager manager = new DeviceTokenManager();
        LocalToken tokenSaved = new LocalToken(manager.getLocalToken());
        if (tokenSaved.getDeviceToken() != null && !tokenSaved.isSaved()) {
            final LocalToken localToken = new LocalToken(tokenSaved);
            localToken.setUserId(UserSingleton.getInstance().getUser().getId());
            try {
                manager.registerDeviceToken(localToken, UserSingleton.getInstance().getUser().getToken(), new Response.Listener<JsonElement>() {
                    @Override
                    public void onResponse(JsonElement response) {
                        localToken.setSaved(true);
                        manager.updateLocalToken(localToken);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEVICE_TOKEN_ERROR, error.getMessage());
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
