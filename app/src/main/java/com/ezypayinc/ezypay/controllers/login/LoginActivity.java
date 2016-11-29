package com.ezypayinc.ezypay.controllers.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.LoginView;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.ILoginPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    // UI references.
    private EditText mPasswordView, mEmailView;
    private Button mLogInButton, mSignInButton;
    private ProgressDialog mProgressDiaolog;
    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.validateUser();
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
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLogInButton = (Button) findViewById(R.id.log_in_button);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);

        //set listeners
        mLogInButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }

    private void setupProgressDialog(){
        mProgressDiaolog = new ProgressDialog(this);
        mProgressDiaolog.setCancelable(false);
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
        mProgressDiaolog.show();
        mProgressDiaolog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDiaolog.dismiss();
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
        } else if(view.equals(mSignInButton)) {
            Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
            startActivity(intent);
        }
    }
}

