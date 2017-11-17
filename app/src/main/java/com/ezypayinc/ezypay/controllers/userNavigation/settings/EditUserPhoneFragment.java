package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;


public class EditUserPhoneFragment extends Fragment {

    public EditUserPhoneFragment() {
        // Required empty public constructor
    }

    public static EditUserPhoneFragment newInstance(String param1, String param2) {
        EditUserPhoneFragment fragment = new EditUserPhoneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_phone, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
