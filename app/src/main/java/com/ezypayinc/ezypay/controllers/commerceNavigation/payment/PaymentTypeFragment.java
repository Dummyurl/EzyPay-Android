package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Payment;

public class PaymentTypeFragment extends Fragment implements  View.OnClickListener {

    private Button mSyncButton, mQuickPayButton;
    private int mTableNumber;

    public PaymentTypeFragment() {
        // Required empty public constructor
    }

    public static PaymentTypeFragment newInstance(int tableNumber) {
        PaymentTypeFragment fragment = new PaymentTypeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_payment_type, container, false);
        mSyncButton = rootView.findViewById(R.id.payment_type_sync_button);
        mQuickPayButton = rootView.findViewById(R.id.payment_type_quickPay_button);
        mSyncButton.setOnClickListener(this);
        mQuickPayButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.payment_type_quickPay_button:
                quickPayAction();
                break;
            case R.id.payment_type_sync_button:
                syncPayAction();
                break;
            default:
                break;
        }
    }

    private void quickPayAction() {
        //Todo change this implementation to a real payment
        Fragment fragment =QRCodeGeneratorFragment.newInstance(new Payment());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_commerce_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    private void syncPayAction() {
        Fragment fragment = PaymentDetailFragment.newInstance(mTableNumber);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_commerce_main_container, fragment).
                addToBackStack(null).
                commit();
    }
}
