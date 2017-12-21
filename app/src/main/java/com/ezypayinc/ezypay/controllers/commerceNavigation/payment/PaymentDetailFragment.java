package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.ezypayinc.ezypay.R;

public class PaymentDetailFragment extends Fragment {

    private NumberPicker mCurrencyPicker;

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
        mCurrencyPicker = rootView.findViewById(R.id.payment_detail_numberPicker);
        String[] data = new String[]{"Berlin", "Moscow", "Tokyo", "Paris"};
        mCurrencyPicker.setMinValue(0);
        mCurrencyPicker.setMaxValue(data.length-1);
        mCurrencyPicker.setDisplayedValues(data);
        return  rootView;
    }

}