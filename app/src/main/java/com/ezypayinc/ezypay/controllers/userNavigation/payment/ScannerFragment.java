package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezypayinc.ezypay.R;


public class ScannerFragment extends Fragment implements View.OnClickListener{

    private Button btnInitScanner, btnSplitPayment, btnPay;

    public ScannerFragment() {
        // Required empty public constructor
    }

    public static ScannerFragment newInstance() {
        ScannerFragment fragment = new ScannerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_scanner, container, false);
        btnInitScanner = (Button) rootView.findViewById(R.id.scanner_btnInitScanner);
        btnSplitPayment = (Button) rootView.findViewById(R.id.scanner_btnSplitPayment);
        btnPay = (Button) rootView.findViewById(R.id.scanner_btnPay);
        btnInitScanner.setOnClickListener(this);
        btnSplitPayment.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnSplitPayment)) {
            Intent intent = new Intent(getActivity(), PaymentMainActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
