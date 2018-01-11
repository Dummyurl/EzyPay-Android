package com.ezypayinc.ezypay.controllers.login.commerce;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.navigation.MainCommerceActivity;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInBankInformationView;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;

public class SignInBankInformationFragment extends Fragment implements View.OnClickListener, ISignInBankInformationView {

    private EditText mIdEditText, mAccountNumberEditText, mAccountNameEditText, mBankEditText;
    private Button mSaveButton;
    private ProgressDialog mProgressDialog;

    public SignInBankInformationFragment() {
        // Required empty public constructor
    }

    public static SignInBankInformationFragment newInstance() {
        SignInBankInformationFragment fragment = new SignInBankInformationFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_sign_in_bank_information, container, false);
        mIdEditText = rootView.findViewById(R.id.user_id_editText_sig_in_commerce_bank_account_fragment);
        mAccountNumberEditText = rootView.findViewById(R.id.account_number_editText_sig_in_commerce_bank_account_fragment);
        mAccountNameEditText = rootView.findViewById(R.id.commerce_account_editText_sig_in_commerce_bank_account_fragment);
        mBankEditText = rootView.findViewById(R.id.commerce_bank_name_editText_sig_in_commerce_bank_account_fragment);
        mSaveButton = rootView.findViewById(R.id.save_button_sig_in_commerce_bank_account_fragment);
        setupProgressDialog();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.title_bank_account_fragment);
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void goToHomeView() {
        Intent intent = new Intent(getActivity(), MainCommerceActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            setupProgressDialog();
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void commerceIdRequiredError() {
        mIdEditText.setError(getString(R.string.error_field_required));
    }

    @Override
    public void accountNumberRequiredError() {
        mAccountNumberEditText.setError(getString(R.string.error_field_required));

    }

    @Override
    public void accountNameRequiredError() {
        mAccountNameEditText.setError(getString(R.string.error_field_required));
    }

    @Override
    public void bankRequiredError() {
        mBankEditText.setError(getString(R.string.error_field_required));
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }
}
