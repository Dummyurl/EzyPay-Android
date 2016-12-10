package com.ezypayinc.ezypay.controllers.userNavigation.cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.ScannerFragment;

public class CardsMainActivity extends AppCompatActivity {

    private OnBackPressedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState == null){
            int cardId = this.getIntent().getIntExtra(CardDetailFragment.CARD_ID, 0);
            int viewType = this.getIntent().getIntExtra(CardDetailFragment.VIEW_TYPE, 0);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cards_main_container, CardDetailFragment.newInstance(cardId, viewType))
                    .commit();
        }
    }

    public void setListener(OnBackPressedListener listener) {
        mListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mListener != null) {
            if(mListener.onBackPressed()) {
                super.onBackPressed();
            }
        }
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }
}
