package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IPaymentDetailView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.CommercePresenter.IPaymentDetailPresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.PaymentDetailPresenter;

public class PaymentDetailFragment extends Fragment implements IPaymentDetailView, View.OnClickListener {

    private NumberPicker mCurrencyPicker;
    private Button mSubmitButtton;
    private EditText mPaymentCostEditText;
    private IPaymentDetailPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private int mTableNumber;


    public PaymentDetailFragment() {
        // Required empty public constructor
    }

    public static PaymentDetailFragment newInstance(int tableNumber) {
        PaymentDetailFragment fragment = new PaymentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PaymentCommerceMainActivity.TABLE_NUMBER_KEY, tableNumber);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mTableNumber = getArguments().getInt(PaymentCommerceMainActivity.TABLE_NUMBER_KEY, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_payment_detail, container, false);
        setupProgressDialog();
        mCurrencyPicker = rootView.findViewById(R.id.payment_detail_numberPicker);
        mPaymentCostEditText = rootView.findViewById(R.id.payment_detail_cost_editText);
        mSubmitButtton = rootView.findViewById(R.id.payment_detail_send_button);
        mPresenter = new PaymentDetailPresenter(this);
        mPresenter.getCurrencies();
        mSubmitButtton.setOnClickListener(this);
        return  rootView;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void loadCurrencies(String[] currencyName) {
        mCurrencyPicker.setMinValue(0);
        mCurrencyPicker.setMaxValue(currencyName.length - 1);
        mCurrencyPicker.setDisplayedValues(currencyName);
    }

    public void navigateToQRFragment(Payment payment) {
        QRCodeGeneratorFragment fragment = QRCodeGeneratorFragment.newInstance(payment);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_commerce_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void onClick(View v) {
        float cost = Float.parseFloat(mPaymentCostEditText.getText().toString());
        int currencyIndex = mCurrencyPicker.getValue();
        mPresenter.savePaymentCost(cost, mTableNumber, currencyIndex, false);
    }
}