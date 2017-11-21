package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;


public class EditUserPhoneFragment extends Fragment {

    private EditText mEdtPhoneCode, mEdtPhoneNumber;

    public EditUserPhoneFragment() {
        // Required empty public constructor
    }

    public static EditUserPhoneFragment newInstance() {
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
        View rootView = inflater.inflate(R.layout.fragment_edit_user_phone, container, false);
        mEdtPhoneCode = (EditText) rootView.findViewById(R.id.code_editText_edit_user_phone_fragment);
        mEdtPhoneNumber = (EditText) rootView.findViewById(R.id.phone_editText_edit_user_phone_fragment);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
