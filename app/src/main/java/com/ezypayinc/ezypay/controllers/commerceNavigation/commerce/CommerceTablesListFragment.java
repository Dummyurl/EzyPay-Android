package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.adapters.CommerceTablesAdapter;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews.ICommerceTableListView;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.PaymentCommerceMainActivity;
import com.ezypayinc.ezypay.model.Table;
import com.ezypayinc.ezypay.presenter.CommercePresenter.CommerceTableListPresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.ICommerceTableListPresenter;

import java.util.ArrayList;
import java.util.List;

public class CommerceTablesListFragment extends Fragment implements ICommerceTableListView, CommerceTablesAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;
    private ProgressDialog mProgressDialog;
    private ICommerceTableListPresenter mPresenter;
    private CommerceTablesAdapter mListAdapter;


    public CommerceTablesListFragment() {
        // Required empty public constructor
    }

    public static CommerceTablesListFragment newInstance() {
        CommerceTablesListFragment fragment = new CommerceTablesListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_commerce_tables_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.commerce_tables_list_recyclerView);
        mSwipeContainer = rootView.findViewById(R.id.commerce_tables_list_swipeContainer);
        mListAdapter = new CommerceTablesAdapter(new ArrayList<Table>(), getContext(), this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mListAdapter);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getTables();
            }
        });
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupProgressDialog();
        if (mPresenter == null) {
            mPresenter = new CommerceTableListPresenter(this);
        }
        mPresenter.getTables();
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
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
    public void showTables(List<Table> tables) {
        mSwipeContainer.setRefreshing(false);
        mListAdapter.clear();
        mListAdapter.addAll(tables);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void OnItemClickListener(Table table) {
        Intent intent = new Intent(getActivity(), PaymentCommerceMainActivity.class);
        getActivity().startActivity(intent);
    }
}
