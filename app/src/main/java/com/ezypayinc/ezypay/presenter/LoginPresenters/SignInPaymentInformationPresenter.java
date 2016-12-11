package com.ezypayinc.ezypay.presenter.LoginPresenters;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInPaymentInformationView;
import com.ezypayinc.ezypay.manager.CardManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmList;

/**
 * Created by gustavoquesada on 11/25/16.
 */

public class SignInPaymentInformationPresenter implements ISignInPaymentInformationPresenter {
    private SignInPaymentInformationView view;

    public SignInPaymentInformationPresenter(SignInPaymentInformationView view) {
        this.view = view;
    }

    @Override
    public void populateSpinners() {
        //populate month spinner
        this.view.populateMonthSpinner(R.array.months);

        //populate year spinner
        ArrayList<String> years = new ArrayList<String>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            years.add(Integer.toString(i));
        }
        this.view.populateYearSpinner(years);
    }

    @Override
    public void registerRecord(String cardNumber, int cvv, int moth, int year) {
        Card card = new Card();
        card.setNumber(cardNumber);
        card.setCvv(cvv);
        card.setMonth(moth);
        card.setYear(year);
        User user = UserSingleton.getInstance().getUser();
        if(user.getCards() == null){
            user.setCards(new RealmList<Card>());
        }
        user.getCards().add(card);
        saveUser(user);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    public boolean validateFields(String cardNumber, String cvv) {
        if(TextUtils.isEmpty(cardNumber)) {
            this.view.setErrorMessage(R.id.sign_in_card_number, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(cvv)) {
            this.view.setErrorMessage(R.id.sign_in_cvv, R.string.error_field_required);
            return false;
        }
        return true;
    }

    private void saveUser(final User user) {
        final UserManager userManager = new UserManager();
        try {
            userManager.saveUserInServer(user, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        user.setId(userManager.parseRegisterUser(response));
                        login(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void login(final User user) {
        final UserManager userManager = new UserManager();
        try {
            userManager.loginMethod(user.getEmail(), user.getPassword(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        User userFromLogin = userManager.parseLoginResponse(response);
                        user.setToken(userFromLogin.getToken());
                        UserSingleton.getInstance().setUser(user);
                        saveCard(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveCard(final User user) {
        final CardManager cardManager = new CardManager();
        final UserManager userManager = new UserManager();
        try {
            cardManager.saveCardInServer(user.getCards().get(0),  new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Card card = cardManager.parseSaveCardResponse(response);
                        user.getCards().clear();
                        user.getCards().add(card);
                        UserSingleton.getInstance().setUser(user);
                        userManager.addUser(user);
                        view.navigateToHome();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    view.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
