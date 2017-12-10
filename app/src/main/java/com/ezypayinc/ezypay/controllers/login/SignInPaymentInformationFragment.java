package com.ezypayinc.ezypay.controllers.login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.Helpers.CardValidatorTextWatcher;
import com.ezypayinc.ezypay.controllers.Helpers.CreditCardValidator;
import com.ezypayinc.ezypay.controllers.Helpers.ExpDateValidatorTextWatcher;
import com.ezypayinc.ezypay.controllers.Helpers.RightDrawableOnTouchListener;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInPaymentInformationView;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.LoginPresenters.ISignInPaymentInformationPresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.SignInPaymentInformationPresenter;

import java.util.ArrayList;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class SignInPaymentInformationFragment extends Fragment implements View.OnClickListener, SignInPaymentInformationView{

    private EditText edtCardNumber, edtExpDate, edtCvv;
    private View mRootView;
    private OnFinishWizard listener;
    private ISignInPaymentInformationPresenter presenter;
    private static final int SCAN_REQUEST_CODE = 200;
    private ProgressDialog mProgressDialog;

    public SignInPaymentInformationFragment() {
        // Required empty public constructor
    }

    public static SignInPaymentInformationFragment newInstance() {
        SignInPaymentInformationFragment fragment = new SignInPaymentInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View rootView = inflater.inflate(R.layout.fragment_sign_in_payment_information, container, false);
        initComponents(rootView);
        presenter = new SignInPaymentInformationPresenter(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        listener = (SignInPaymentInformationFragment.OnFinishWizard) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.listener = null;
        this.presenter.onDestroy();
        this.presenter = null;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setCancelable(false);
    }

    public void initComponents(View rootView) {
        mRootView = rootView;
        edtCardNumber = (EditText)rootView.findViewById(R.id.sign_in_card_number);
        edtExpDate = (EditText) rootView.findViewById(R.id.sign_in_exp_date);
        edtCvv = (EditText)rootView.findViewById(R.id.sign_in_cvv);
        edtExpDate.addTextChangedListener(new ExpDateValidatorTextWatcher(edtExpDate));
        edtCardNumber.addTextChangedListener(new CardValidatorTextWatcher());

        Button btnSaveCard = (Button) rootView.findViewById(R.id.sign_in_save_card);
        btnSaveCard.setOnClickListener(this);
        edtCardNumber.setOnTouchListener(new RightDrawableOnTouchListener(edtCardNumber) {
            @Override
            public boolean onDrawableTouch(MotionEvent event) {
                onScanPress();
                return true;
            }
        });
    }

    private void onScanPress() {
        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                edtCardNumber.setText(scanResult.cardNumber);
                int year = scanResult.expiryYear % 2000;
                edtExpDate.setText(scanResult.expiryMonth + "/" + year);
                edtCvv.setText(scanResult.cvv);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String cardNumber = edtCardNumber.getText().toString();
        String cvvString = edtCvv.getText().toString();
        String expDate = edtExpDate.getText().toString();
        CreditCard creditCard = new CreditCard();
        creditCard.cardNumber = cardNumber;
        if(presenter.validateFields(cardNumber, cvvString, expDate, creditCard.getCardType())) {
            CreditCardValidator validator = new CreditCardValidator();
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCcv(Integer.parseInt(cvvString));
            card.setExpirationDate(expDate);
            card.setCardVendor(validator.getCardType(creditCard.getCardType()));
            card.setFavorite(1);
            presenter.registerRecord(card);
        }
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
        EditText view = (EditText) mRootView.findViewById(component);
        view.setError(getString(error));
        view.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getActivity());
    }

    @Override
    public void navigateToHome() {
        listener.onFinishWizard();
    }


    public interface OnFinishWizard {
        void onFinishWizard();
    }
}


