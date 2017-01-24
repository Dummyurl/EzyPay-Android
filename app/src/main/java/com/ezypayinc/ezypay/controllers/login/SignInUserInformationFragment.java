package com.ezypayinc.ezypay.controllers.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInUserInformationView;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ISignInUserInformationPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.SignInUserInformationPresenter;

public class SignInUserInformationFragment extends Fragment implements View.OnClickListener, SignInUserInformationView {

    public OnChangeViewListener listener;
    private EditText edtName, edtLastName, edtPhoneNumber, edtEmail,
            edtPassword;
    private View mRootView;
    private ISignInUserInformationPresenter mPresenter;

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
        validateUser();
        return rootView;
    }

    public void onStart() {
        super.onStart();
        listener = (OnChangeViewListener) getActivity();

    }

    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
        mPresenter.onDestroy();
        mPresenter = null;
    }

    public void initUIComponents(View rootView) {
        mRootView = rootView;
        edtName = (EditText)rootView.findViewById(R.id.sign_in_name);
        edtLastName = (EditText)rootView.findViewById(R.id.sign_in_lastname);
        edtPhoneNumber = (EditText)rootView.findViewById(R.id.sign_in_phone_number);
        edtEmail = (EditText)rootView.findViewById(R.id.sign_in_email);
        edtPassword = (EditText)rootView.findViewById(R.id.sign_in_password);
        Button btnSaveUser = (Button)rootView.findViewById(R.id.sign_in_save_user_information);
        btnSaveUser.setOnClickListener(this);
    }

    private void validateUser(){
        User user = mPresenter.validateUser();
        if(user != null) {
            edtName.setText(user.getName());
            edtLastName.setText(user.getLastName());
            edtPhoneNumber.setText(user.getPhoneNumber());
            edtEmail.setText(user.getEmail());
        }
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
        mPresenter.registerUser(name, lastname, phoneNumber, email, password);
    }


    public interface  OnChangeViewListener {
        void changeView(Fragment newFragment);
    }
}
