package com.ezypayinc.ezypay.controllers.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.SplashScreen.SplashScreenActivity;
import com.ezypayinc.ezypay.controllers.commerceNavigation.navigation.MainCommerceActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.CustomNotification;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.notifications.INotificationHandler;
import com.ezypayinc.ezypay.notifications.NotificationFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn, btnSignInRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateUserSession();
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.activity_main_btn_signIn);
        btnSignInRestaurant = findViewById(R.id.activity_main_btn_signInRestaurant);
        btnSignIn.setOnClickListener(this);
        btnSignInRestaurant.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_btn_signIn:
                navigateToLogInView(1);
                break;
            case R.id.activity_main_btn_signInRestaurant:
                navigateToLogInView(2);
                break;
            default:
                break;
        }
    }

    void validateUserSession() {
        UserManager manager = new UserManager();
        User user = manager.getUser();
        if(user != null && user.getToken() != null) {
            UserSingleton userSingleton = UserSingleton.getInstance();
            userSingleton.setUser(user);
            if(user.getUserType() == 1) {
                navigateToHomeActivity();
            } else {
                navigateToCommerceHomeActivity();
            }
        }
    }

    void navigateToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToCommerceHomeActivity() {
        Intent intent = new Intent(MainActivity.this, MainCommerceActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToLogInView(int userType) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Bundle extra = new Bundle();
        extra.putInt(LoginActivity.USER_TYPE_KEY, userType);
        intent.putExtras(extra);
        startActivity(intent);
    }
}
