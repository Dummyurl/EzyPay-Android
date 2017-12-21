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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGeneratorFragment extends Fragment {

    private ImageView mQRImageView;

    public QRCodeGeneratorFragment() {
        // Required empty public constructor
    }

    public static QRCodeGeneratorFragment newInstance() {
        QRCodeGeneratorFragment fragment = new QRCodeGeneratorFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_qrcode_generator, container, false);
        mQRImageView = rootView.findViewById(R.id.qr_code_generator_image_view);
        generateQRCode();
        return rootView;
    }

    private void generateQRCode() {
        String content = "Testing QR code writer on android";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            mQRImageView.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
