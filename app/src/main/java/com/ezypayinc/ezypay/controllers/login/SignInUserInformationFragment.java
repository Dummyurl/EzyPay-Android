package com.ezypayinc.ezypay.controllers.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInUserInformationView;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.PhoneCodeListFragment;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ISignInUserInformationPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.SignInUserInformationPresenter;

public class SignInUserInformationFragment extends Fragment implements View.OnClickListener, SignInUserInformationView, OnPhoneCodeSelected {

    public OnChangeViewListener listener;
    private EditText edtName, edtLastName, edtPhoneNumber, edtEmail,
            edtPassword, edtPhoneCode;
    private View mRootView;
    private ISignInUserInformationPresenter mPresenter;
    private PhoneCode mPhoneCode;
    private ProgressDialog mProgressDialog;

    public SignInUserInformationFragment() {
        // Required empty public constructor
    }

    public static SignInUserInformationFragment newInstance() {
        SignInUserInformationFragment fragment = new SignInUserInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SignInUserInformationPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in_user_information, container, false);
        initUIComponents(rootView);
        return rootView;
    }

    public void onStart() {
        super.onStart();
        listener = (OnChangeViewListener) getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPhoneCode != null) {
            edtPhoneCode.setText(mPhoneCode.getPhonecode());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SingInActivity) context).setPhoneSelectedListener(this);
        setupProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        mPresenter.onDestroy();
        mPresenter = null;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setCancelable(false);
    }

    public void initUIComponents(View rootView) {
        mRootView = rootView;
        edtName = (EditText)rootView.findViewById(R.id.sign_in_name);
        edtLastName = (EditText)rootView.findViewById(R.id.sign_in_lastname);
        edtPhoneNumber = (EditText)rootView.findViewById(R.id.sign_in_phone_number);
        edtEmail = (EditText)rootView.findViewById(R.id.sign_in_email);
        edtPassword = (EditText)rootView.findViewById(R.id.sign_in_password);
        edtPhoneCode = (EditText)rootView.findViewById(R.id.sign_in_code_phone_number);
        Button btnSaveUser = (Button)rootView.findViewById(R.id.sign_in_save_user_information);
        btnSaveUser.setOnClickListener(this);
        edtPhoneCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    PhoneCodeListFragment fragment = PhoneCodeListFragment.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().
                            replace(R.id.sign_in_container_view, fragment).
                            addToBackStack(null).
                            commit();
                }
            }
        });
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
    public void setErrorMessage(int component, int error) {
        EditText editText = (EditText) mRootView.findViewById(component);
        editText.setError(getString(error));
        editText.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    public void userIsAlreadySaved() {
        String message = getString(R.string.error_email_already_assigned);
        DialogBuilder dialogBuilder = new DialogBuilder(this.getContext());
        dialogBuilder.defaultAlertDialog(message);
    }

    @Override
    public void navigateToPaymentView() {
        listener.changeView(SignInPaymentInformationFragment.newInstance());
    }

    @Override
    public void onClick(View view) {
        String name = edtName.getText().toString();
        String lastname = edtLastName.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String code = edtPhoneCode.getText().toString();
        mPresenter.registerUser(name, lastname, phoneNumber, email, password, code);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneCode = phoneCode;
    }


    public interface  OnChangeViewListener {
        void changeView(Fragment newFragment);
    }
}
