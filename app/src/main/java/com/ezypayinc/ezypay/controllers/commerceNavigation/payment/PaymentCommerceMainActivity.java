package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;

public class PaymentCommerceMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_commerce_main);

        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                getSupportFragmentManager().beginTransaction().
                        add(R.id.payment_commerce_main_container, PaymentTypeFragment.newInstance())
                        .commit();

            }
        }
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
    }

}
