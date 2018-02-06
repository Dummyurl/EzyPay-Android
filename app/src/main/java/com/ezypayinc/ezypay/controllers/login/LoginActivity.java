package com.ezypayinc.ezypay.controllers.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.navigation.MainCommerceActivity;
import com.ezypayinc.ezypay.controllers.login.commerce.SignInCommerceMainActivity;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.LoginView;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.model.Credentials;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ILoginPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.LoginPresenter;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    // UI references.
    private EditText mPasswordView, mEmailView;
    private Button mLogInButton, mCommerceRegisterButton;
    private ProgressDialog mProgressDialog;
    private ILoginPresenter loginPresenter;
    private LinearLayout mUserLoginContainer, mCommerceLoginContainer;
    private CallbackManager mCallbackManager;
    private LoginButton mBtnFacebookLogin;
    private TextView mCreateAccountLabel;
    private TextView mForgotPassword;

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
        mCallbackManager = CallbackManager.Factory.create();
        initUIComponents();
        setupProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
        loginPresenter = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initUIComponents() {
        // Set up the login form.
        if(userType == 1) {
            mEmailView = findViewById(R.id.email);
            mPasswordView = findViewById(R.id.password);
            mLogInButton = findViewById(R.id.log_in_button);
            mBtnFacebookLogin = findViewById(R.id.btn_facebook_login);
            mCreateAccountLabel = findViewById(R.id.sign_in_textView);
            mForgotPassword = findViewById(R.id.forgot_password_textView);
            mCreateAccountLabel.setOnClickListener(this);
            mForgotPassword.setOnClickListener(this);
            setFacebookLoginButton();
        } else {
            mEmailView = findViewById(R.id.commerce_email);
            mPasswordView = findViewById(R.id.commerce_password);
            mLogInButton =  findViewById(R.id.log_in_commerce_button);
            mCommerceRegisterButton = findViewById(R.id.register_commerce_button);
            mForgotPassword = findViewById(R.id.commerce_forgot_password_textView);
            mForgotPassword.setOnClickListener(this);
            mCommerceRegisterButton.setOnClickListener(this);
        }

        mUserLoginContainer = findViewById((R.id.userLogIn));
        mCommerceLoginContainer = findViewById((R.id.commerceLogin));

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

    private void setFacebookLoginButton() {
        mBtnFacebookLogin.setReadPermissions(Arrays.asList("email"));
        mBtnFacebookLogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        parseFacebookLogin(object, loginResult);
                        if(UserSingleton.getInstance().getUser() != null) {
                            loginPresenter.validateFacebookLogin(UserSingleton.getInstance().getUser());
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, email, first_name, last_name,  picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.facebook_login_cancel_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.facebook_login_error_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseFacebookLogin(JSONObject response, LoginResult loginResult) {
        if (response != null) {
            User user = new User();
            try {
                user.setEmail(response.getString("email"));
                user.setName(response.getString("first_name"));
                user.setLastName(response.getString("last_name"));
                user.setCredentials(new Credentials());
                user.getCredentials().setCredential(response.getString("id"));
                user.getCredentials().setPlatform("Facebook");
                user.getCredentials().setPlatformToken(loginResult.getAccessToken().getToken());
                UserSingleton.getInstance().setUser(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

    public void navigateToCommerceHome() {
        Intent intent = new Intent(LoginActivity.this, MainCommerceActivity.class);
        startActivity(intent);
    }

    public void navigateToSignUser() {
        Intent intent = new Intent(LoginActivity.this, SingInActivity.class);
        startActivity(intent);
    }

    private void navigateToSignCommerce() {
        Intent intent = new Intent(LoginActivity.this, SignInCommerceMainActivity.class);
        startActivity(intent);
    }

    private void navigateToResetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(mLogInButton)) {
            attemptLogin();
        } else if(view.equals(mCreateAccountLabel)) {
            navigateToSignUser();
        } else if (view.equals(mCommerceRegisterButton)) {
            navigateToSignCommerce();
        } else if(view.equals(mForgotPassword)) {
            navigateToResetPassword();
        }
    }
}

