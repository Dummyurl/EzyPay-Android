package com.ezypayinc.ezypay.presenter.LoginPresenters.commerce;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInBankInformationView;
import com.ezypayinc.ezypay.helpers.CameraHelper;
import com.ezypayinc.ezypay.manager.BankAccountManager;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.BankAccount;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONException;


public class SignInBankInformationPresenter implements ISignInBankInformationPresenter {

    private ISignInBankInformationView mView;

    public SignInBankInformationPresenter(ISignInBankInformationView view) {
        mView = view;
    }


    @Override
    public void saveUserInformation(BankAccount bankAccount) {
        if(validateFields(bankAccount)) {
            User commerce = UserSingleton.getInstance().getUser();
            if(commerce != null) {
                saveUser(commerce, bankAccount);
            }
        }
    }

    private boolean validateFields(BankAccount bankAccount) {
        if(bankAccount != null) {
            if (TextUtils.isEmpty(bankAccount.getUserIdentification())) {
                mView.commerceIdRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getAccountNumber())) {
                mView.accountNumberRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getUserAccount())) {
                mView.accountNameRequiredError();
                return false;
            }

            if (TextUtils.isEmpty(bankAccount.getBank())) {
                mView.bankRequiredError();
                return false;
            }
            return true;
        }
        return false;
    }

    private void saveUser(final User user, final BankAccount bankAccount) {
        mView.showProgressDialog();
        final UserManager userManager = new UserManager();
        try {
            userManager.saveUserInServer(user, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    User newUser = userManager.parseRegisterUser(response, user);
                    login(newUser, bankAccount);
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

    private void login(final User user, final BankAccount bankAccount) {
        final UserManager userManager = new UserManager();
        try {
            userManager.loginMethod(user.getEmail(), user.getPassword(), new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    User userFromLogin = userManager.parseLoginResponse(response);
                    user.setToken(userFromLogin.getToken());
                    UserSingleton.getInstance().setUser(user);
                    saveBankAccount(user, bankAccount);
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

    private void saveBankAccount(User user,BankAccount bankAccount) {
        mView.showProgressDialog();
        BankAccountManager manager = new BankAccountManager();
        try {
            manager.registerAccount(bankAccount, user, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                    uploadImage();
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

    private void uploadImage() {
        Bitmap commercePhoto = UserSingleton.getInstance().getUserProfilePhoto();
        byte[] encodedImage = CameraHelper.getImageEncoded(commercePhoto);
        User user  = UserSingleton.getInstance().getUser();
        UserManager manager = new UserManager();
        try {
            manager.uploadImge(encodedImage, user, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    mView.dismissProgressDialog();
                    mView.goToHomeView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.onNetworkError(error);
                    mView.dismissProgressDialog();
                }
            });
        } catch (JSONException e) {
            mView.dismissProgressDialog();
            e.printStackTrace();
        }
    }
}
