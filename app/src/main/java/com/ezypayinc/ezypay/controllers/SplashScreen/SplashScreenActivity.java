package com.ezypayinc.ezypay.controllers.SplashScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.MainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(){
            public void run() {
                try {
                    sleep(3000);
                    navigateToMainActivity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }


    void navigateToMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
