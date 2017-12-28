package com.ezypayinc.ezypay.controllers.commerceNavigation.settings;

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
import android.widget.ImageView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardsMainActivity;
import com.ezypayinc.ezypay.model.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommerceSettingsFragment extends Fragment {
    private EditText mName, mEmail, mPhoneNumber;
    private ImageView mProfileImage;


    public CommerceSettingsFragment() {
    }

    public static CommerceSettingsFragment newInstance() {
        CommerceSettingsFragment fragment = new CommerceSettingsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_commerce_settings, container, false);
        mProfileImage = rootView.findViewById(R.id.commerce_settings_image_profile_view);
        mName = rootView.findViewById(R.id.commerce_settings_view_name);
        mEmail = rootView.findViewById(R.id.commerce_settings_view_email);
        mPhoneNumber = rootView.findViewById(R.id.commerce_settings_view_phone_number);
        setHasOptionsMenu(true);
        getUser();
        return rootView;
    }

    private void getUser() {
        User user = UserSingleton.getInstance().getUser();
        if(user != null) {
            mName.setText(user.getName());
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
                intent = new Intent(getActivity(), MainCommerceSettingsActivity.class);
                getActivity().startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
