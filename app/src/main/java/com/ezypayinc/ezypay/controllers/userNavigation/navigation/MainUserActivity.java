package com.ezypayinc.ezypay.controllers.userNavigation.navigation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.notifications.NotificationsFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.PaymentMainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.SettingsFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainUserActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private int itemSelected;
    private OnBarcodeScanned barcodeScannedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        setupNavigationBar(savedInstanceState);
        if(savedInstanceState == null){
            Fragment fragment = ScannerFragment.newInstance();
            barcodeScannedListener = (OnBarcodeScanned) fragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_activity_main_user, fragment)
                    .commit();
        }

    }

    public void setupNavigationBar(Bundle savedInstance) {
        bottomBar = BottomBar.attach(this, savedInstance);
        bottomBar.setItemsFromMenu(R.menu.navigation_bar_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                itemSelected = itemId;
                Fragment newFragment = null;
                switch (itemId) {
                    case R.id.scanner_item:
                        newFragment = ScannerFragment.newInstance();
                        barcodeScannedListener = (OnBarcodeScanned) newFragment;
                        break;
                    case R.id.notifications_item:
                        newFragment = NotificationsFragment.newInstance();
                        barcodeScannedListener = null;
                        break;
                    case R.id.settings_item:
                        newFragment = SettingsFragment.newInstance();
                        barcodeScannedListener = null;
                        break;
                }


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_user, newFragment)
                        .commit();
            }
        });
        bottomBar.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bottom_bar_background));
        bottomBar.setActiveTabColor("#C2185B");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
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

    public interface OnBarcodeScanned {
        void scanResult(String qrCode);
    }
}
