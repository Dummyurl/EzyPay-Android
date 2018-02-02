package com.ezypayinc.ezypay.controllers.userNavigation.payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezypayinc.ezypay.R;

public class PaymentResultFragment extends Fragment {
    private Button mGoBackButton;

    public PaymentResultFragment() {
        // Required empty public constructor
    }

    public static PaymentResultFragment newInstance() {
        PaymentResultFragment fragment = new PaymentResultFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_payment_result, container, false);
        mGoBackButton = rootView.findViewById(R.id.go_home_button_payment_result_fragment);
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return rootView;
    }

}
