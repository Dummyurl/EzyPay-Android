package com.ezypayinc.ezypay.controllers.commerceNavigation.settings;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.EditUserFragment;

public class MainCommerceSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commerce_settings);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_activity_main_commerce_settings, EditCommerceFragment.newInstance())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
    }
}
