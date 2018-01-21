package com.ezypayinc.ezypay.controllers.commerceNavigation.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.model.Payment;

public class PaymentCommerceMainActivity extends AppCompatActivity {

    public static final String TABLE_NUMBER_KEY  = "TABLE";
    public static final String PAYMENT_KEY  = "PAYMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_commerce_main);

        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                int tableNumber = bundle.getInt(TABLE_NUMBER_KEY, 0);
                if(tableNumber > 0) {
                    getSupportFragmentManager().beginTransaction().
                            add(R.id.payment_commerce_main_container, PaymentTypeFragment.newInstance(tableNumber))
                            .commit();
                } else {
                    Payment payment = bundle.getParcelable(PAYMENT_KEY);
                    if (payment != null) {
                        Fragment fragment = QRCodeGeneratorFragment.newInstance(payment);
                        getSupportFragmentManager().beginTransaction().
                                add(R.id.payment_commerce_main_container, fragment)
                                .commit();;
                    }

                }

            }
        }
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
    }

}
