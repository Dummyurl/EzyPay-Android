package com.ezypayinc.ezypay.presenter.LoginPresenters;

import android.text.TextUtils;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInUserInformationView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;

public class SignInUserInformationPresenter implements ISignInUserInformationPresenter {

    private SignInUserInformationView view;

    public SignInUserInformationPresenter(SignInUserInformationView view) {
        this.view = view;
    }

    @Override
    public User validateUser() {
        UserManager userManager = new UserManager();
        return userManager.getUser();
    }

    @Override
    public void registerUser(String username, String lastName, String phoneNumber, String email, String password) {
        if(validateFields(username, lastName, phoneNumber, email, password)) {
            final User user = new User();
            user.setName(username);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setPassword(password);
            addUser(user);
        }
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    private boolean validateFields(String username, String lastName, String phoneNumber, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            view.setErrorMessage(R.id.sign_in_name, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(lastName)) {
            view.setErrorMessage(R.id.sign_in_lastname, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(phoneNumber)) {
            view.setErrorMessage(R.id.sign_in_phone_number, R.string.error_field_required);
            return false;
        }

        if(TextUtils.isEmpty(email)) {
            view.setErrorMessage(R.id.sign_in_email, R.string.error_field_required);
            return false;
        }

        if(!validateEmail(email)){
            view.setErrorMessage(R.id.sign_in_email, R.string.error_field_required);
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            view.setErrorMessage(R.id.sign_in_password, R.string.error_field_required);
            return false;
        }

        if (password.length() < 4) {
            view.setErrorMessage(R.id.sign_in_password, R.string.error_invalid_password);
            return false;
        }

        return true;
    }

    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    private void addUser(final User user) {
        UserManager manager = new UserManager();
        UserSingleton.getInstance().setUser(user);
        manager.deleteUser();
        manager.addUser(user);
        view.navigateToPaymentView();
    }
}
