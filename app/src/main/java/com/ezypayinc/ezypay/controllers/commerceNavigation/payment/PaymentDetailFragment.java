package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IPaymentDetailView;
import com.ezypayinc.ezypay.presenter.CommercePresenter.IPaymentDetailPresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.PaymentDetailPresenter;

public class PaymentDetailFragment extends Fragment implements IPaymentDetailView {

    private NumberPicker mCurrencyPicker;
    private IPaymentDetailPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    public PaymentDetailFragment() {
        // Required empty public constructor
    }

    public static PaymentDetailFragment newInstance() {
        PaymentDetailFragment fragment = new PaymentDetailFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_payment_detail, container, false);
        setupProgressDialog();
        mCurrencyPicker = rootView.findViewById(R.id.payment_detail_numberPicker);
        mPresenter = new PaymentDetailPresenter(this);
        mPresenter.getCurrencies();
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

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }
}