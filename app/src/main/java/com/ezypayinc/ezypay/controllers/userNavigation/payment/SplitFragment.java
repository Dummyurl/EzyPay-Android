package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ISplitListView;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ISplitListPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.SplitListPresenter;


public class SplitFragment extends Fragment implements View.OnClickListener, ISplitListView, SplitAdapter.ISplitListEvents {

    private Payment mPayment;
    private Button btnNext, btnCancel, btnChange;
    private TextView shortageTextView, totalTextView;
    private EditText editTextChange;
    private ISplitListPresenter mPresenter;
    private RelativeLayout mChangePaymentContainer;
    private SplitAdapter.SplitViewHolder currentCell;

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
        mChangePaymentContainer = (RelativeLayout) rootView.findViewById(R.id.change_payment_container);
        btnNext = (Button)  rootView.findViewById(R.id.split_fragment_next_button);
        btnCancel = (Button)  rootView.findViewById(R.id.split_payment_cancel_button);
        btnChange = (Button)  rootView.findViewById(R.id.split_payment_change_button);
        shortageTextView = (TextView) rootView.findViewById(R.id.split_shortage_textView);
        totalTextView = (TextView)rootView.findViewById(R.id.split_total_texView);
        editTextChange = (EditText)rootView.findViewById(R.id.split_fragment_payment_editText);
        RecyclerView usersRecyclerView = (RecyclerView) rootView.findViewById(R.id.split_fragment_recycler_view);

        btnNext.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        usersRecyclerView.setLayoutManager(mLayoutManager);
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SplitAdapter mAdapter = new SplitAdapter(mPayment, UserSingleton.getInstance().getUser(), getContext(), this);
        usersRecyclerView.setAdapter(mAdapter);

        mPresenter = new SplitListPresenter(this);
        setPaymentData(mPayment, mPayment.getCost());

        editTextChange.setText("0");
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
                goToPaymentView();
                break;
            case R.id.split_payment_cancel_button:
                cancelButtonAction();
                break;
            case R.id.split_payment_change_button:
                changeButtonAction();
                break;
            default:
                break;
        }
    }

    private void goToPaymentView() {
        Fragment fragment = PaymentFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.payment_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    private void cancelButtonAction() {
        mChangePaymentContainer.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusView = getActivity().getCurrentFocus();
        if(focusView != null) {
            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
        editTextChange.setText("0");
    }

    private void changeButtonAction() {
        if(currentCell != null) {
            float quantity = Float.parseFloat(editTextChange.getText().toString());
            mPresenter.changePaymentQuantity(quantity, currentCell, mPayment);
            cancelButtonAction();
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

    @Override
    public boolean onLongTapCell(SplitAdapter.SplitViewHolder holder) {
        mChangePaymentContainer.setVisibility(View.VISIBLE);
        currentCell = holder;
        return true;
    }
}
