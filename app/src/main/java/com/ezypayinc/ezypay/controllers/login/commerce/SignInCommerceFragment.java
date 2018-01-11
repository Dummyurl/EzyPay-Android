package com.ezypayinc.ezypay.controllers.login.commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInCommerceView;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.PhoneCodeListFragment;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.LoginPresenters.commerce.ISignInCommercePresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.commerce.SignInCommercePresenter;

public class SignInCommerceFragment extends Fragment implements View.OnClickListener, OnPhoneCodeSelected, ISignInCommerceView {

    private EditText edtName, edtPhoneNumber, edtEmail,
            edtPassword, edtPhoneCode;
    private PhoneCode mPhoneCode;
    private ProgressDialog mProgressDialog;
    private ISignInCommercePresenter mPresenter;

    public SignInCommerceFragment() {
        // Required empty public constructor
    }

    public static SignInCommerceFragment newInstance() {
        SignInCommerceFragment fragment = new SignInCommerceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in_commerce, container, false);
        initUIComponents(rootView);
        mPresenter = new SignInCommercePresenter(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SignInCommerceMainActivity)getActivity()).setPhoneSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPhoneCode != null) {
            edtPhoneCode.setText(mPhoneCode.getPhonecode());
        }
    }

    public void initUIComponents(View rootView) {
        setupProgressDialog();
        edtName = rootView.findViewById(R.id.sign_in_commerce_name);
        edtPhoneNumber = rootView.findViewById(R.id.sign_in_commerce_phone_number);
        edtEmail = rootView.findViewById(R.id.sign_in_commerce_email);
        edtPassword = rootView.findViewById(R.id.sign_in_commerce_password);
        edtPhoneCode = rootView.findViewById(R.id.sign_in_commerce_code_phone_number);
        Button btnSaveUser = rootView.findViewById(R.id.sign_in_commerce_save_user_button);
        btnSaveUser.setOnClickListener(this);
        edtPhoneCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    PhoneCodeListFragment fragment = PhoneCodeListFragment.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().
                            replace(R.id.sign_in_commerce_container_view, fragment).
                            addToBackStack(null).
                            commit();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        User user = new User();
        String phoneNumber = edtPhoneCode.getText().toString() + " " + edtPhoneNumber.getText().toString();
        user.setName(edtName.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        user.setPhoneNumber(phoneNumber);
        user.setPassword(edtPassword.getText().toString());
        mPresenter.registerCommerce(user, edtPhoneCode.getText().toString());
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneCode = phoneCode;
    }

    @Override
    public void showProgressDialog() {
        if(mProgressDialog == null) {
            setupProgressDialog();
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void dismissProgressDialog() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void goToNextView() {
        Fragment fragment = SignInPhotoCommerceFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sign_in_commerce_container_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void userIsAlreadySaved() {
        String message = getString(R.string.error_email_already_assigned);
        DialogBuilder dialogBuilder = new DialogBuilder(this.getContext());
        dialogBuilder.defaultAlertDialog(message);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void nameRequiredError() {
        edtName.setError(getString(R.string.error_field_required));
        edtName.requestFocus();
    }

    @Override
    public void phoneRequiredError() {
        edtPhoneNumber.setError(getString(R.string.error_field_required));
        edtPhoneNumber.requestFocus();
    }

    @Override
    public void emailRequiredError() {
        edtEmail.setError(getString(R.string.error_field_required));
        edtEmail.requestFocus();
    }

    @Override
    public void passwordRequiredError() {
        edtPassword.setError(getString(R.string.error_field_required));
        edtPassword.requestFocus();
    }

    @Override
    public void emailFormatError() {
        edtEmail.setError(getString(R.string.error_invalid_email));
        edtEmail.requestFocus();
    }

    @Override
    public void passwordFormatError() {
        edtPassword.setError(getString(R.string.error_invalid_password));
        edtPassword.requestFocus();
    }
}
