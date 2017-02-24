package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.PaymentAdapter;

public class PaymentFragment extends Fragment  {

    private RecyclerView paymentRecyclerView;


    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {
        PaymentFragment fragment = new PaymentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        paymentRecyclerView = (RecyclerView) rootView.findViewById(R.id.payment_fragment_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        paymentRecyclerView.setLayoutManager(mLayoutManager);
        paymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        paymentRecyclerView.setAdapter(new PaymentAdapter(null));
        return rootView;
    }

}
