package com.ezypayinc.ezypay.controllers.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.IResetPasswordView;
import com.ezypayinc.ezypay.presenter.LoginPresenters.IResetPasswordPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ResetPasswordPresenter;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, IResetPasswordView {

    private EditText mEmailEditText;
    private Button mResetPasswordButton;
    private ProgressDialog mProgressDialog;
    private IResetPasswordPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmailEditText = findViewById(R.id.reset_password_email_editText);
        mResetPasswordButton = findViewById(R.id.reset_password_button);
        mResetPasswordButton.setOnClickListener(this);
        mPresenter = new ResetPasswordPresenter(this);
        setTitle(R.string.action_reset_password);
        setupProgressDialog();
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        String email = mEmailEditText.getText().toString();
        mPresenter.resetPassword(email);
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
    public void goToLoginActivity() {
        onBackPressed();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, this);
    }
}
