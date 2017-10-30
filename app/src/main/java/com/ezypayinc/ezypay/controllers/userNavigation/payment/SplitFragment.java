package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ISplitListView;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ISplitListPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.SplitListPresenter;


public class SplitFragment extends Fragment implements View.OnClickListener, ISplitListView, SplitAdapter.ISeekBarListener {

    private Payment mPayment;
    private Button btnNext;
    private TextView shortageTextView, totalTextView;
    private ISplitListPresenter mPresenter;

    public SplitFragment() {
        // Required empty public constructor
    }

    public static SplitFragment newInstance(Payment payment) {
        SplitFragment fragment = new SplitFragment();
        Bundle args = new Bundle();
        args.putParcelable(PaymentMainActivity.PAYMENT_KEY, payment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mPayment = getArguments().getParcelable(PaymentMainActivity.PAYMENT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_split, container, false);
        btnNext = (Button)  rootView.findViewById(R.id.split_fragment_next_button);
        shortageTextView = (TextView) rootView.findViewById(R.id.split_shortage_textView);
        totalTextView = (TextView)rootView.findViewById(R.id.split_total_texView);
        btnNext.setOnClickListener(this);
        RecyclerView usersRecyclerView = (RecyclerView) rootView.findViewById(R.id.split_fragment_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        usersRecyclerView.setLayoutManager(mLayoutManager);
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SplitAdapter mAdapter = new SplitAdapter(mPayment, UserSingleton.getInstance().getUser(), getContext(), this);
        usersRecyclerView.setAdapter(mAdapter);
        mPresenter = new SplitListPresenter(this);
        setPaymentData(mPayment, mPayment.getCost());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.split_view_title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.split_fragment_next_button :
                Fragment fragment = PaymentFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.payment_main_container, fragment).
                        addToBackStack(null).
                        commit();
                break;
        }
    }

    @Override
    public void setPaymentData(Payment payment, float shortage) {
        shortageTextView.setText(payment.getCurrency().getCurrencySymbol() + " " + shortage);
        totalTextView.setText(payment.getCurrency().getCurrencySymbol() + " " + payment.getCost());
        btnNext.setEnabled(shortage <= 0);
    }

    @Override
    public int seekBarProgressChanged(int progress, Payment payment, Friend friend) {
        return mPresenter.validateQuantity(progress, payment, friend);
    }

}
