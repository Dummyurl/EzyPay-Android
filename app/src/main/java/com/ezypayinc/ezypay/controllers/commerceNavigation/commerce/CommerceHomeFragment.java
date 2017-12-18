package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.View;


import com.ezypayinc.ezypay.R;

public class CommerceHomeFragment extends Fragment {

    public CommerceHomeFragment() {
        // Required empty public constructor
    }

    public static CommerceHomeFragment newInstance() {
        CommerceHomeFragment fragment = new CommerceHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_commerce_home, container, false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
