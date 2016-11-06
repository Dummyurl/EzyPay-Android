package com.ezypayinc.ezypay.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ezypayinc.ezypay.R;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void showUserLogIn(View v) {
        Intent intent = new Intent(PrincipalActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
