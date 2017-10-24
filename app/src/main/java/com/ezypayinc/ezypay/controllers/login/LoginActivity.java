package com.ezypayinc.ezypay.controllers.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.LoginView;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ILoginPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.LoginPresenter;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    // UI references.
    private EditText mPasswordView, mEmailView;
    private Button mLogInButton;
    private ProgressDialog mProgressDialog;
    private ILoginPresenter loginPresenter;
    private LinearLayout mUserLoginContainer, mCommerceLoginContainer;

    private int userType;
    public static final String USER_TYPE_KEY = "userType";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null) {
            userType = intent.getExtras().getInt(USER_TYPE_KEY);
        }
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        initUIComponents();
        setupProgressDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
        loginPresenter = null;
    }

    private void initUIComponents() {
        // Set up the login form.
        if(userType == 1) {
            mEmailView = (EditText) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
            mLogInButton = (Button) findViewById(R.id.log_in_button);
        } else {
            mEmailView = (EditText) findViewById(R.id.commerce_email);
            mPasswordView = (EditText) findViewById(R.id.commerce_password);
            mLogInButton = (Button) findViewById(R.id.log_in_commerce_button);
        }

        mUserLoginContainer = (LinearLayout) findViewById((R.id.userLogIn));
        mCommerceLoginContainer = (LinearLayout) findViewById((R.id.commerceLogin));

        if(userType == 1) {
            mUserLoginContainer.setVisibility(View.VISIBLE);
            mCommerceLoginContainer.setVisibility(View.GONE);
        } else {
            mCommerceLoginContainer.setVisibility(View.VISIBLE);
            mUserLoginContainer.setVisibility(View.GONE);
        }

        //set listeners
        mLogInButton.setOnClickListener(this);
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        loginPresenter.loginMethod(email, password);
    }

    @Override
    public void setUsernameError(int error) {
        mEmailView.setError(getString(error));
        mEmailView.requestFocus();
    }

    @Override
    public void setPasswordError(int error) {
        mPasswordView.setError(getString(error));
        mPasswordView.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, LoginActivity.this);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(mLogInButton)) {
            attemptLogin();
        }
    }
}

