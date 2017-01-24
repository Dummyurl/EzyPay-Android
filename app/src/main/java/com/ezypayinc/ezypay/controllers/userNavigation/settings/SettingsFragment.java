package com.ezypayinc.ezypay.controllers.userNavigation.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardsMainActivity;
import com.ezypayinc.ezypay.model.User;

public class SettingsFragment extends Fragment {

    private EditText mName, mLastName, mEmail, mPhoneNumber;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        initUIComponents(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
    }

    private void initUIComponents(View rootView) {
        mName = (EditText) rootView.findViewById(R.id.settings_view_name);
        mLastName = (EditText) rootView.findViewById(R.id.settings_view_lastName);
        mEmail = (EditText) rootView.findViewById(R.id.settings_view_email);
        mPhoneNumber = (EditText) rootView.findViewById(R.id.settings_view_phone_number);
    }

    private void getUser() {
        User user = UserSingleton.getInstance().getUser();
        mName.setText(user.getName());
        mLastName.setText(user.getLastName());
        mEmail.setText(user.getEmail());
        mPhoneNumber.setText(user.getPhoneNumber());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.settings_view_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.credit_cards_item:
                intent = new Intent(getActivity(), CardsMainActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.edit_user_item:
                intent = new Intent(getActivity(), SettingsMainActivity.class);
                getActivity().startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
