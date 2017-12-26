package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.commerceNavigation.payment.interfaceViews.IQRCodeGeneratorView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.CommercePresenter.IQRCodeGeneratorPresenter;
import com.ezypayinc.ezypay.presenter.CommercePresenter.QRCodeGeneratorPresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGeneratorFragment extends Fragment implements IQRCodeGeneratorView {

    private ImageView mQRImageView;
    private Payment mPayment;
    private static final String PAYMENT_KEY = "PAYMENT";
    private IQRCodeGeneratorPresenter mPresenter;

    public QRCodeGeneratorFragment() {
        // Required empty public constructor
    }

    public static QRCodeGeneratorFragment newInstance(Payment payment) {
        QRCodeGeneratorFragment fragment = new QRCodeGeneratorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PAYMENT_KEY, payment);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPayment = getArguments().getParcelable(PAYMENT_KEY);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.qr_code_generator_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qrcode_generator, container, false);
        mPresenter = new QRCodeGeneratorPresenter(this);
        mQRImageView = rootView.findViewById(R.id.qr_code_generator_image_view);
        mPresenter.generateQrCode(mPayment);
        return rootView;
    }


    @Override
    public void showQRImage(Bitmap bitmap) {
        mQRImageView.setImageBitmap(bitmap);
    }
}
