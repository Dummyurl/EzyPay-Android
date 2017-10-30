package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.IScannerView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.IScannerPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ScannerPresenter;
import com.google.zxing.integration.android.IntentIntegrator;

public class ScannerFragment extends Fragment implements MainUserActivity.OnBarcodeScanned, IScannerView, View.OnClickListener {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private RelativeLayout preScannerView, requestServiceView;
    private LinearLayout restaurantOptionsContainer, paymentOptionsContainer;
    private IScannerPresenter presenter;
    private TextView labelPaymentOption;
    private ImageView commerceImageView;
    private Button btnScanner, btnPayBill, btnCallWaiter, btnAlone, btnSplit;

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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_scanner, container, false);
        preScannerView = (RelativeLayout) rootView.findViewById(R.id.pre_scanner_view);
        requestServiceView = (RelativeLayout) rootView.findViewById(R.id.request_service_view);
        restaurantOptionsContainer = (LinearLayout) rootView.findViewById(R.id.restaurant_options_container);
        paymentOptionsContainer = (LinearLayout) rootView.findViewById(R.id.payment_options_container);
        labelPaymentOption = (TextView) rootView.findViewById(R.id.payment_option_textView);
        commerceImageView = (ImageView) rootView.findViewById(R.id.commerce_imageView);
        btnScanner = (Button) rootView.findViewById(R.id.scanner_fragment_btn_scanner);
        btnCallWaiter = (Button) rootView.findViewById(R.id.scanner_fragment_btnCallWaiter);
        btnPayBill = (Button) rootView.findViewById(R.id.scanner_fragment_btnPayBill);
        btnSplit = (Button) rootView.findViewById(R.id.split_button);
        btnAlone = (Button) rootView.findViewById(R.id.alone_button);
        btnScanner.setOnClickListener(this);
        btnCallWaiter.setOnClickListener(this);
        btnPayBill.setOnClickListener(this);
        btnSplit.setOnClickListener(this);
        btnAlone.setOnClickListener(this);
        return rootView;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setCancelable(false);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mActivity.setTitle(R.string.scanner_view_title);
        setupProgressDialog();
        if(presenter == null) {
            presenter = new ScannerPresenter(this);
            presenter.validatePayment();
        }
    }

    @Override
    public void scanResult(String qrCode) {
        presenter.addPayment(qrCode);
    }

    @Override
    public void showRestaurantDetail(Payment payment) {
        fadeInAnimation(requestServiceView, preScannerView);
        boolean paymentHasCost = payment.getCost() > 0;
        paymentOptionsContainer.setVisibility(paymentHasCost? View.VISIBLE : View.GONE);
        restaurantOptionsContainer.setVisibility(paymentHasCost? View.GONE : View.VISIBLE);
        labelPaymentOption.setVisibility(paymentHasCost? View.VISIBLE : View.GONE);
        mActivity.setTitle(payment.getCommerce().getName());
        presenter.loadCommerceImage(mActivity, commerceImageView);
        setHasOptionsMenu(true);
    }

    @Override
    public void showScannerView() {
        fadeOutAnimation(requestServiceView, preScannerView);
        mActivity.setTitle(R.string.scanner_view_title);
        setHasOptionsMenu(false);
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
        ErrorHelper.handleError(error, mActivity);
    }

    @Override
    public void goToContactsList(Payment payment) {
        Intent intent = new Intent(getActivity(), PaymentMainActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(PaymentMainActivity.PAYMENT_KEY, payment);
        intent.putExtras(extras);
        getActivity().startActivity(intent);
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
            presenter.deletePayment();
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
            presenter.sendBillRequest();
        } else if(view.getId() == btnCallWaiter.getId()) {
            presenter.callWaiter();
        } else if(view.getId() == btnSplit.getId()) {
            presenter.splitPayment();
        }
    }
}
