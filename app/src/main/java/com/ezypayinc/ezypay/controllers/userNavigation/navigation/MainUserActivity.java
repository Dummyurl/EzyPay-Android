package com.ezypayinc.ezypay.controllers.userNavigation.navigation;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.notifications.NotificationsFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.SettingsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainUserActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    private int itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        setupNavigationBar(savedInstanceState);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_activity_main_user, ScannerFragment.newInstance())
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
                        break;
                    case R.id.notifications_item:
                        newFragment = NotificationsFragment.newInstance();
                        break;
                    case R.id.settings_item:
                        newFragment = SettingsFragment.newInstance();
                        break;
                }


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_user, newFragment)
                        .commit();
            }
        });
        bottomBar.setActiveTabColor("#C2185B");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }
}
