package com.ezypayinc.ezypay.controllers.userNavigation.navigation;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.userNavigation.history.HistoryFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.SettingsFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainUserActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private OnBarcodeScanned barcodeScannedListener;
    public static final String SCANNER_FRAGMENT_TAG  = "SCANNER_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
        setupNavigationBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("Bundle", bundle.toString());
        }
    }

    public void setupNavigationBar() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment newFragment = null;
                String tag = null;
                switch (tabId) {
                    case R.id.scanner_item:
                        newFragment = ScannerFragment.newInstance();
                        tag = SCANNER_FRAGMENT_TAG;
                        barcodeScannedListener = (OnBarcodeScanned) newFragment;
                        break;
                    case R.id.notifications_item:
                        newFragment = HistoryFragment.newInstance();
                        barcodeScannedListener = null;
                        break;
                    case R.id.settings_item:
                        newFragment = SettingsFragment.newInstance();
                        barcodeScannedListener = null;
                        break;
                }


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_user, newFragment, tag)
                        .commit();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                if (barcodeScannedListener != null) {
                    barcodeScannedListener.scanResult(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setBarcodeScannedListener(OnBarcodeScanned listener) {
        barcodeScannedListener = listener;
    }

    public interface OnBarcodeScanned {
        void scanResult(String qrCode);
    }
}
