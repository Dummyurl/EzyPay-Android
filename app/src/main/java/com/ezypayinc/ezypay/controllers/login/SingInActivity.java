package com.ezypayinc.ezypay.controllers.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.controllers.userNavigation.navigation.MainUserActivity;
import com.ezypayinc.ezypay.model.PhoneCode;


public class SingInActivity extends AppCompatActivity implements SignInUserInformationFragment.OnChangeViewListener,
    SignInPaymentInformationFragment.OnFinishWizard , OnPhoneCodeSelected {

    private OnPhoneCodeSelected mPhoneSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        if(savedInstanceState == null) {
            SignInUserInformationFragment userInformationFragment = SignInUserInformationFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.sign_in_container_view, userInformationFragment);
            fragmentTransaction.commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void setPhoneSelectedListener(OnPhoneCodeSelected listener) {
        mPhoneSelected = listener;
    }

    @Override
    public void changeView(Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sign_in_container_view, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFinishWizard() {
        Intent intent = new Intent(SingInActivity.this, MainUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneSelected.phoneCodeSelected(phoneCode);
    }
}
