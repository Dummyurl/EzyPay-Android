package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ezypayinc.ezypay.R;

public class CardsMainActivity extends AppCompatActivity {

    private OnBackPressedListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cards_main_container, CardsFragment.newInstance())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setListener(OnBackPressedListener listener) {
        mListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(mListener != null) {
            if(mListener.onBackPressed()) {
                mListener = null;
                getSupportFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
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
}
