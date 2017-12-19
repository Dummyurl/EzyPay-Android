package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews.ICommerceHomeView;
import com.ezypayinc.ezypay.presenter.CommercePresenter.CommerceHomePresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.ICommerceHomePresenter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommerceHomeFragment extends Fragment implements View.OnClickListener, ICommerceHomeView{
    private ImageView commerceImageView;
    private TextView userLoggedTextView, changeEmployeeTextView;
    private Button btnGenerateQr;
    private ProgressDialog mProgressDialog;
    private ICommerceHomePresenter mPresenter;


    public CommerceHomeFragment() {
        // Required empty public constructor
    }

    public static CommerceHomeFragment newInstance() {
        CommerceHomeFragment fragment = new CommerceHomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_commerce_home, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {
        mPresenter = new CommerceHomePresenter(this);
        commerceImageView = rootView.findViewById(R.id.commerce_home_imageView);
        userLoggedTextView = rootView.findViewById(R.id.commerce_home_user_logged_in);
        changeEmployeeTextView = rootView.findViewById(R.id.commerce_home_change_employee);
        btnGenerateQr = rootView.findViewById(R.id.commerce_home_generate_qr);
        btnGenerateQr.setOnClickListener(this);
        mPresenter.setupView();
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // setupProgressDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }

    @Override
    public void displayUserInformation(String avatar, String username, String viewTitle) {
        userLoggedTextView.setText(username);
        this.getActivity().setTitle(viewTitle);
        Picasso.with(getContext()).load(avatar).transform(new CropCircleTransformation()).into(commerceImageView);
    }
}
