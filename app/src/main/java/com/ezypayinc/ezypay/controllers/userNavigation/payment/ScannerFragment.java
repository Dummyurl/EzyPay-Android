package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ScannerView;
import com.ezypayinc.ezypay.model.Ticket;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.IScannerPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ScannerPresenter;
import com.google.zxing.integration.android.IntentIntegrator;

public class ScannerFragment extends Fragment implements MainUserActivity.OnBarcodeScanned, ScannerView, View.OnClickListener {
    private RelativeLayout preScannerView, requestServiceView;
    private IScannerPresenter presenter;
    private Button btnScanner, btnCallWaiter, btnPayBill;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_scanner, container, false);
        preScannerView = (RelativeLayout) rootView.findViewById(R.id.pre_scanner_view);
        requestServiceView = (RelativeLayout) rootView.findViewById(R.id.request_service_view);
        btnScanner = (Button) rootView.findViewById(R.id.scanner_fragment_btn_scanner);
        btnCallWaiter = (Button) rootView.findViewById(R.id.scanner_fragment_btnCallWaiter);
        btnPayBill = (Button) rootView.findViewById(R.id.scanner_fragment_btnPayBill);
        btnScanner.setOnClickListener(this);
        btnCallWaiter.setOnClickListener(this);
        btnPayBill.setOnClickListener(this);
        presenter = new ScannerPresenter(this);
        presenter.validateTicket();
        return rootView;
    }

    public void fadeInAnimation(final View viewFadeIn, final View viewToHide) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(viewFadeIn, "alpha", .3f, 1f);
        fadeIn.setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeIn);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewToHide.setVisibility(View.GONE);
                viewFadeIn.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    public void fadeOutAnimation(final View viewFadeOut, final View viewToShow) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(viewFadeOut, "alpha",  1f, .3f);
        fadeOut.setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewFadeOut.setVisibility(View.GONE);
                viewToShow.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void scanResult(String qrCode) {
        presenter.addTicket(qrCode);
    }

    @Override
    public void showRestaurantDetail(Ticket ticket) {
        fadeInAnimation(requestServiceView, preScannerView);
        setHasOptionsMenu(true);
    }

    @Override
    public void showScannerView() {
        fadeOutAnimation(requestServiceView, preScannerView);
        setHasOptionsMenu(false);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.scanner_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.scanner_delete_ticket_item) {
            presenter.deleteTicket();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnScanner.getId()) {
            final Activity activity = getActivity();
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
        } else if (view.getId() == btnPayBill.getId()) {
            Intent intent = new Intent(getActivity(), PaymentMainActivity.class);
            getActivity().startActivity(intent);
        }
    }
}
