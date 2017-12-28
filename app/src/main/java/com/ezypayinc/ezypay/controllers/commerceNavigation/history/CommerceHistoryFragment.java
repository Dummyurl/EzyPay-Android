package com.ezypayinc.ezypay.controllers.commerceNavigation.history;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.history.adapters.CommerceHistoryAdapter;
import com.ezypayinc.ezypay.controllers.commerceNavigation.history.interfaceViews.ICommerceHistoryView;
import com.ezypayinc.ezypay.model.CommerceHistory;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.presenter.CommerceHistoryPresenters.CommerceHistoryPresenter;
import com.ezypayinc.ezypay.presenter.CommerceHistoryPresenters.ICommerceHistoryPresenter;

import java.util.ArrayList;
import java.util.List;


public class CommerceHistoryFragment extends Fragment implements ICommerceHistoryView {

    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private ICommerceHistoryPresenter mPresenter;

    public CommerceHistoryFragment() {
        // Required empty public constructor
    }
    public static CommerceHistoryFragment newInstance() {
        CommerceHistoryFragment fragment = new CommerceHistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_commerce_history, container, false);
        mRecyclerView = rootView.findViewById(R.id.commerce_history_fragment_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPresenter = new CommerceHistoryPresenter(this);
        mPresenter.getCommerceHistory();
        return  rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.history_view_title);
        setupProgressDialog();
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showHistoryRecords(List<CommerceHistory> commerceHistory, List<HistoryDate> datesList) {
        mRecyclerView.setAdapter(new CommerceHistoryAdapter(commerceHistory, datesList));
    }

    @Override
    public void showProgressDialog() {
        if(mProgressDialog == null)
        {
            setupProgressDialog();
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }
}
