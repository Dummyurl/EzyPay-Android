package com.ezypayinc.ezypay.controllers.userNavigation.history;

import android.app.Activity;
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
import com.ezypayinc.ezypay.controllers.userNavigation.history.adapters.HistoryListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.history.interfaceViews.IHistoryView;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.UserHistory;
import com.ezypayinc.ezypay.presenter.HistoryPresenters.IUserHistoryPresenter;
import com.ezypayinc.ezypay.presenter.HistoryPresenters.UserHistoryPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ScannerPresenter;

import java.util.List;

public class HistoryFragment extends Fragment implements IHistoryView {

    private IUserHistoryPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_user_history, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.user_history_fragment_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.history_view_title);
        setupProgressDialog();
        if(mPresenter == null) {
            mPresenter = new UserHistoryPresenter(this);
            mPresenter.getUserHistory();
        }
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showHistoryRecords(List<UserHistory> userHistory, List<HistoryDate> datesList) {
        mRecyclerView.setAdapter(new HistoryListAdapter(userHistory, datesList, getContext()));
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
