package com.ezypayinc.ezypay.controllers.commerceNavigation.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.CommerceHomeFragment;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.CommerceTablesListFragment;
import com.ezypayinc.ezypay.controllers.commerceNavigation.history.CommerceHistoryFragment;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.CommerceSettingsFragment;
import com.ezypayinc.ezypay.controllers.login.MainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.notifications.INotificationHandler;
import com.ezypayinc.ezypay.notifications.NotificationFactory;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainCommerceActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commerce);
        setUser();
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
        User currentUser = UserSingleton.getInstance().getUser();
        if (currentUser != null && currentUser.getUserType() == 4) {
            setupEmployeeNavigationBar();
        } else {
            setupNavigationBar(currentUser);
        }
        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("category") != null && currentUser != null) {
            int category = Integer.parseInt(bundle.getString("category"));
            INotificationHandler notificationHandler = NotificationFactory.initNotificationHandler(category);
            CustomNotification customNotification = notificationHandler.parseNotification(bundle);
            notificationHandler.notificationAction(customNotification, this);
        } else if (currentUser == null){
            Intent intent = new Intent(MainCommerceActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setUser() {
        User user = UserSingleton.getInstance().getUser();
        if (user == null) {
            UserManager manager = new UserManager();
            user = manager.getUser();
            if (user != null) {
                UserSingleton.getInstance().setUser(user);
            }
        }
    }


    private void setupEmployeeNavigationBar() {
        bottomBar = findViewById(R.id.commerce_bottomBar);
        bottomBar.setItems(R.xml.bottom_bar_employee_tabs);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment newFragment = null;
                switch (tabId) {
                    case R.id.employee_home_item:
                        newFragment = CommerceHomeFragment.newInstance();
                        break;
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_activity_main_commerce, newFragment)
                        .commit();
            }
        });
    }

    private void setupNavigationBar(final User currentUser) {
        bottomBar = findViewById(R.id.commerce_bottomBar);
        bottomBar.setItems(R.xml.bottombar_commerce_tabs);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment newFragment = null;
                switch (tabId) {
                    case R.id.commerce_home_item:
                        if (currentUser != null && currentUser.getUserType() == 2) {
                            newFragment = CommerceTablesListFragment.newInstance();
                        } else {
                            newFragment = CommerceHomeFragment.newInstance();
                        }
                        break;
                    case R.id.notifications_item:
                        newFragment = CommerceHistoryFragment.newInstance();
                        break;
                    case R.id.settings_item:
                        newFragment = CommerceSettingsFragment.newInstance();
                        break;
                }
                if(currentUser != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_activity_main_commerce, newFragment)
                            .commit();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState();
    }

}
