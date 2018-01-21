package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews.ICommerceHomeView;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.PaymentCommerceMainActivity;
import com.ezypayinc.ezypay.controllers.login.MainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.ISettingsView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.CommercePresenter.CommerceHomePresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.CommerceTableListPresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.ICommerceHomePresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.ISettingsPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.SettingsPresenter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommerceHomeFragment extends Fragment implements View.OnClickListener, ICommerceHomeView, ISettingsView {
    private ImageView commerceImageView;
    private TextView userLoggedTextView, changeEmployeeTextView;
    private Button btnGenerateQr;
    private ProgressDialog mProgressDialog;
    private ICommerceHomePresenter mPresenter;
    private ISettingsPresenter mSettingsPresenter;

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
        setupProgressDialog();
        mPresenter = new CommerceHomePresenter(this);
        mSettingsPresenter = new SettingsPresenter(this);
        commerceImageView = rootView.findViewById(R.id.commerce_home_imageView);
        userLoggedTextView = rootView.findViewById(R.id.commerce_home_user_logged_in);
        changeEmployeeTextView = rootView.findViewById(R.id.commerce_home_change_employee);
        btnGenerateQr = rootView.findViewById(R.id.commerce_home_generate_qr);
        btnGenerateQr.setOnClickListener(this);
        changeEmployeeTextView.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.commerce_home_generate_qr :
                mPresenter.generateQrCode();
                break;
            case R.id.commerce_home_change_employee:
                mSettingsPresenter.logOutAction();
                break;
        }
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

    public void displayCommerceTableListView() {
        Fragment fragment = CommerceTablesListFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.container_activity_main_commerce, fragment).
                addToBackStack(null).
                commit();
    }

    @Override
    public void generateQRCode(Payment payment) {
        Intent intent = new Intent(getActivity(), PaymentCommerceMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PaymentCommerceMainActivity.PAYMENT_KEY, payment);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void logOutAction() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
