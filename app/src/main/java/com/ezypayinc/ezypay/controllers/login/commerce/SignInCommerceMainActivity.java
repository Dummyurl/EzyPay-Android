package com.ezypayinc.ezypay.controllers.login.commerce;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.model.PhoneCode;

public class SignInCommerceMainActivity extends AppCompatActivity implements OnPhoneCodeSelected {

    private OnPhoneCodeSelected mPhoneSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_commerce_main);
        if(savedInstanceState == null) {
            SignInCommerceFragment fragment = SignInCommerceFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.sign_in_commerce_container_view, fragment);
            fragmentTransaction.commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneSelected.phoneCodeSelected(phoneCode);
    }

    public void setPhoneSelectedListener(OnPhoneCodeSelected listener) {
        mPhoneSelected = listener;
    }
}
