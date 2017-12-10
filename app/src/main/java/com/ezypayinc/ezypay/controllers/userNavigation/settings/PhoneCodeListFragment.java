package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
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
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.adapters.PhoneCodeListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditPhoneView;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EditUserPhonePresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.IEditUserPhonePresenter;

import java.util.List;

public class PhoneCodeListFragment extends Fragment implements IEditPhoneView, OnPhoneCodeSelected {

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private List<PhoneCode> mPhoneCodeList;
    private IEditUserPhonePresenter mPresenter;
    private OnPhoneCodeSelected mPhoneCodeSelected;

    public PhoneCodeListFragment() {
        // Required empty public constructor
    }

    public static PhoneCodeListFragment newInstance() {
        PhoneCodeListFragment fragment = new PhoneCodeListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_phone_code_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.phone_code_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setupProgressDialog();
        if (mPresenter == null) {
            mPresenter = new EditUserPhonePresenter(this);
        }
        mPhoneCodeSelected = ((OnPhoneCodeSelected)context);
        mPresenter.getPhoneCodeList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void loadPhoneCodeList(List<PhoneCode> phoneCodeList) {
        mPhoneCodeList = phoneCodeList;
        PhoneCodeListAdapter adapter = new PhoneCodeListAdapter(mPhoneCodeList, this);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void showProgressDialog() {
        if(mProgressDialog == null) {
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

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        if(mPhoneCodeSelected != null) {
            phoneCode.setPhonecode("+" + phoneCode.getPhonecode());
            mPhoneCodeSelected.phoneCodeSelected(phoneCode);
            getActivity().onBackPressed();
        }
    }
}
