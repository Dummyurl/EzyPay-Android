package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.bankAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.bankAccount.interfaceViews.IBankAccountView;
import com.ezypayinc.ezypay.model.BankAccount;
import com.ezypayinc.ezypay.presenter.CommerceSettingsPresenters.BankAccountPresenter;
import com.ezypayinc.ezypay.presenter.CommerceSettingsPresenters.IBankAccountPresenter;


public class BankAccountFragment extends Fragment implements View.OnClickListener, IBankAccountView {

    private EditText mCommerceIdEditText, mAccountNumberEditText, mAccountNameEditText, mBankEditText;
    private Button mSaveButton;
    private ProgressDialog mProgressDialog;
    private IBankAccountPresenter mPresenter;
    private BankAccount mBankAccount;

    public BankAccountFragment() {
        // Required empty public constructor
    }

    public static BankAccountFragment newInstance() {
        BankAccountFragment fragment = new BankAccountFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_bank_account, container, false);
        mCommerceIdEditText = rootView.findViewById(R.id.user_id_editText_bank_account_fragment);
        mAccountNumberEditText = rootView.findViewById(R.id.account_number_editText_bank_account_fragment);
        mAccountNameEditText = rootView.findViewById(R.id.commerce_account_editText_bank_account_fragment);
        mBankEditText = rootView.findViewById(R.id.commerce_bank_name_editText_bank_account_fragment);
        mSaveButton = rootView.findViewById(R.id.save_button_bank_account_fragment);
        mSaveButton.setOnClickListener(this);
        mPresenter = new BankAccountPresenter(this);
        setupProgressDialog();
        mPresenter.getBankAccount();
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
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUserIdentification(mCommerceIdEditText.getText().toString());
        bankAccount.setAccountNumber(mAccountNumberEditText.getText().toString());
        bankAccount.setUserAccount(mAccountNameEditText.getText().toString());
        bankAccount.setBank(mBankEditText.getText().toString());
        if(mBankAccount != null) {
            mPresenter.updateBankAccount(bankAccount);
        } else {
            mPresenter.insertBankAccount(bankAccount);
        }
    }

    @Override
    public void populateAccount(BankAccount bankAccount) {
        mBankAccount = bankAccount;
        if(bankAccount != null) {
            mCommerceIdEditText.setText(bankAccount.getUserIdentification());
            mAccountNumberEditText.setText(bankAccount.getAccountNumber());
            mAccountNameEditText.setText(bankAccount.getUserAccount());
            mBankEditText.setText(bankAccount.getBank());
        }
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
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void goToSettingsView() {
        getActivity().onBackPressed();
    }

    @Override
    public void commerceIdRequiredError() {
        mCommerceIdEditText.setError(getString(R.string.error_field_required));
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

}
