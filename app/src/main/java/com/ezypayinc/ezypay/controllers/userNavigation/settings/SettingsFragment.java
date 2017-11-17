package com.ezypayinc.ezypay.controllers.userNavigation.settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardsMainActivity;
import com.ezypayinc.ezypay.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SettingsFragment extends Fragment {

    private EditText mName, mLastName, mEmail, mPhoneNumber;
    private ImageView mProfileImage;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.settings_view_title);
    }

    private void initUIComponents(View rootView) {
        mName = (EditText) rootView.findViewById(R.id.settings_view_name);
        mLastName = (EditText) rootView.findViewById(R.id.settings_view_lastName);
        mEmail = (EditText) rootView.findViewById(R.id.settings_view_email);
        mPhoneNumber = (EditText) rootView.findViewById(R.id.settings_view_phone_number);
        mProfileImage = (ImageView)rootView.findViewById(R.id.image_profile_view);
    }

    private void getUser() {
        User user = UserSingleton.getInstance().getUser();
        if(user != null) {
            mName.setText(user.getName());
            mLastName.setText(user.getLastName());
            mEmail.setText(user.getEmail());
            mPhoneNumber.setText(user.getPhoneNumber());
            getImage(user);
        }
    }

    private void getImage(User user) {
        Picasso.with(getContext()).load(user.getAvatar()).transform(new CropCircleTransformation()).into(mProfileImage);
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
