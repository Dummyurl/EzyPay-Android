package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.model.Payment;

public class PaymentMainActivity extends AppCompatActivity {

    public static final String PAYMENT_KEY = "payment_key";
    public static final String PAYMENT_FRAGMENT_TAG  = "PAYMENT_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_main);

        if(savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null) {
                Payment payment = bundle.getParcelable(PAYMENT_KEY);
                if(payment.getFriends() != null && payment.getFriends().size() > 0) {
                    getSupportFragmentManager().beginTransaction().
                            add(R.id.payment_main_container, PaymentFragment.newInstance(payment), PAYMENT_FRAGMENT_TAG)
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction().
                            add(R.id.payment_main_container, ContactListFragment.newInstance(payment))
                            .commit();
                }
            }
        }
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
