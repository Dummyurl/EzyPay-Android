package com.ezypayinc.ezypay.controllers.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.SplashScreen.SplashScreenActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn, btnSignInRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validateUserSession();
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.activity_main_btn_signIn);
        btnSignInRestaurant = (Button) findViewById(R.id.activity_main_btn_signInRestaurant);
        btnSignIn.setOnClickListener(this);
        btnSignInRestaurant.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_btn_signIn:
                navigateToLogInView();
                break;
            case R.id.activity_main_btn_signInRestaurant:
                navigateToLogInView();
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
            navigateToHomeActivity();
        }
    }

    void navigateLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, MainUserActivity.class);
        startActivity(intent);
        finish();
    }

    void navigateToLogInView() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
