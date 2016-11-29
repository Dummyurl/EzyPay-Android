package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.camera.BarcodeTrackerFactory;
import com.ezypayinc.ezypay.controllers.camera.CameraSourcePreview;
import com.ezypayinc.ezypay.controllers.camera.GraphicOverlay;
import com.ezypayinc.ezypay.controllers.camera.IBarcodeReaderListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class ScannerFragment extends Fragment implements View.OnClickListener, IBarcodeReaderListener{

    private LinearLayout scannerLayout, scannerResult;
    private CameraSourcePreview mPreview;
    private GraphicOverlay mGraphicOverlay;
    private CameraSource mCameraSource;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_scanner, container, false);
        mPreview = (CameraSourcePreview) rootView.findViewById(R.id.preview);
        scannerLayout = (LinearLayout) rootView.findViewById(R.id.scanner_layout);
        scannerResult = (LinearLayout) rootView.findViewById(R.id.scanner_result_scanner);
        mGraphicOverlay = (GraphicOverlay) rootView.findViewById(R.id.overlay);

        /*BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getContext()).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeFactory.setListener(this);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());

        mCameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .build();*/
        return rootView;
    }

    //starting the preview
    private void startCameraSource() {
        try {
            mPreview.start(mCameraSource, mGraphicOverlay);
        } catch (IOException e) {
            mCameraSource.release();
            mCameraSource = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //startCameraSource(); //start
    }

    @Override
    public void onPause() {
        super.onPause();
       // mPreview.stop(); //stop
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // mCameraSource.release(); //release the resources
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onBarcodeChanged(String barcode) {
        mPreview.stop();
        mCameraSource.release();
        scannerLayout.setVisibility(View.GONE);
        scannerResult.setVisibility(View.VISIBLE);

    }
}
