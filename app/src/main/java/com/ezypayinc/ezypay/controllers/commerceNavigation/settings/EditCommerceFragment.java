package com.ezypayinc.ezypay.controllers.commerceNavigation.settings;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;

public class EditCommerceFragment extends Fragment {


    public EditCommerceFragment() {
        // Required empty public constructor
    }

    public static EditCommerceFragment newInstance() {
        EditCommerceFragment fragment = new EditCommerceFragment();
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
        return inflater.inflate(R.layout.fragment_edit_commerce, container, false);
    }
}
