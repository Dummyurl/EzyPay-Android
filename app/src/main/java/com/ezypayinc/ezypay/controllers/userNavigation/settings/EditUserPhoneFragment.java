package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.helpers.ISettingsActivity;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.PhoneCode;
import com.ezypayinc.ezypay.model.User;


public class EditUserPhoneFragment extends Fragment implements OnPhoneCodeSelected, View.OnClickListener {

    private EditText mEdtPhoneCode, mEdtPhoneNumber;
    private PhoneCode mPhoneCode;
    private Button mSaveButton;
    private int mContainerId;

    private static final String CONTAINER_KEY = "CONTAINER";

    public EditUserPhoneFragment() {
        // Required empty public constructor
    }

    public static EditUserPhoneFragment newInstance(int containerId) {
        EditUserPhoneFragment fragment = new EditUserPhoneFragment();
        Bundle args = new Bundle();
        args.putInt(CONTAINER_KEY, containerId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mContainerId = getArguments().getInt(CONTAINER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_user_phone, container, false);
        mEdtPhoneCode = rootView.findViewById(R.id.code_editText_edit_user_phone_fragment);
        mEdtPhoneNumber = rootView.findViewById(R.id.phone_editText_edit_user_phone_fragment);
        mSaveButton = rootView.findViewById(R.id.save_button_edit_user_phone_fragment);
        mEdtPhoneCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    PhoneCodeListFragment fragment = PhoneCodeListFragment.newInstance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().
                            replace(mContainerId, fragment).
                            addToBackStack(null).
                            commit();
                }
            }
        });
        mSaveButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPhoneCode != null) {
            mEdtPhoneCode.setText(mPhoneCode.getPhonecode());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ISettingsActivity) context).setPhoneSelectedListener(this);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneCode = phoneCode;
    }

    @Override
    public void onClick(View v) {
        String phoneNumber = mEdtPhoneCode.getText().toString() + " " + mEdtPhoneNumber.getText().toString();
        User user = UserSingleton.getInstance().getUser();
        user = new UserManager().updateUserPhone(user, phoneNumber);
        UserSingleton.getInstance().setUser(user);
        getActivity().onBackPressed();
    }
}
