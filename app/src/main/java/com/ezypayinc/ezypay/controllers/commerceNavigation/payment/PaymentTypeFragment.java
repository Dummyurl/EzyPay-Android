package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IPaymentTypeView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.CommercePresenter.IPaymentTypePresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.PaymentTypePresenter;

public class PaymentTypeFragment extends Fragment implements  View.OnClickListener, IPaymentTypeView {

    private Button mSyncButton, mQuickPayButton;
    private int mTableNumber;
    private IPaymentTypePresenter mPresenter;

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
        mPresenter = new PaymentTypePresenter(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.payment_type_title);
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

    private void syncPayAction() {
        mPresenter.setupSyncPayAction(mTableNumber);
    }

    private void quickPayAction() {
        Fragment fragment = PaymentDetailFragment.newInstance(mTableNumber);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_commerce_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void goToSyncPayAction(Payment payment) {
        Fragment fragment =QRCodeGeneratorFragment.newInstance(payment);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_commerce_main_container, fragment).
                addToBackStack(null).
                commit();
    }
}
