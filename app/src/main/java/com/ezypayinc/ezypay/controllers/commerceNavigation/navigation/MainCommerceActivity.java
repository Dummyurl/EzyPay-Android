package com.ezypayinc.ezypay.controllers.commerceNavigation.navigation;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.CommerceHomeFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.history.HistoryFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.SettingsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainCommerceActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commerce);
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
        setupNavigationBar();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("Bundle", bundle.toString());
        }
    }

    public void setupNavigationBar() {
        bottomBar = (BottomBar) findViewById(R.id.commerce_bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment newFragment = null;
                switch (tabId) {
                    case R.id.commerce_home_item:
                        newFragment = CommerceHomeFragment.newInstance();
                        break;
                    case R.id.notifications_item:
                        newFragment = HistoryFragment.newInstance();
                        break;
                    case R.id.settings_item:
                        newFragment = SettingsFragment.newInstance();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_commerce, newFragment)
                        .commit();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState();
    }

}
