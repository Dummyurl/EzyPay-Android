package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;

public class EmployeeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_activity_main_employee, EmployeeListFragment.newInstance())
                .commit();
    }

}
