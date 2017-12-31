package com.ezypayinc.ezypay.controllers.commerceNavigation.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.EditUserPasswordFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.EditUserPhoneFragment;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditUserView;
import com.ezypayinc.ezypay.helpers.CameraHelper;
import com.ezypayinc.ezypay.helpers.ISettingsActivity;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.CommerceSettingsPresenters.EditCommercePresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.IEditUserPresenter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EditCommerceFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, IEditUserView, OnChangeImageListener {

    private EditText mEdtName, mEdtEmail, mEdtPhoneNumber, mEdtPassword;
    private ImageView mProfileImage;
    private ProgressDialog mProgressDialog;
    private Bitmap newUserImage;
    private IEditUserPresenter mPresenter;
    private View rootView;


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
        rootView = inflater.inflate(R.layout.fragment_edit_commerce, container, false);
        initUIComponents();
        setupProgressDialog();
        mPresenter = new EditCommercePresenter(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ISettingsActivity) context).setImageListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getUser();
    }

    private void initUIComponents() {
        mEdtName = rootView.findViewById(R.id.name_editText_edit_commerce_fragment);
        mEdtEmail = rootView.findViewById(R.id.email_editText_edit_commerce_fragment);
        mEdtPhoneNumber = rootView.findViewById(R.id.phoneNumber_editText_edit_commerce_fragment);
        mEdtPassword = rootView.findViewById(R.id.password_editText_edit_commerce_fragment);
        mProfileImage = rootView.findViewById(R.id.image_profile_view_edit_commerce_fragment);
        Button mBtnSave = rootView.findViewById(R.id.save_button_edit_commerce_fragment);
        mBtnSave.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        mEdtPassword.setOnFocusChangeListener(this);
        mEdtPhoneNumber.setOnFocusChangeListener(this);
    }


    private void getUser() {
        User user = UserSingleton.getInstance().getUser();
        mEdtName.setText(user.getName());
        mEdtEmail.setText(user.getEmail());
        mEdtPhoneNumber.setText(user.getPhoneNumber());
        getImage(user);
    }

    private void getImage(User user) {
        Picasso.with(getContext()).load(user.getAvatar()).transform(new CropCircleTransformation()).into(mProfileImage);
    }

    private void setupProgressDialog() {
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }

    private void goToPhoneFragment() {
        Fragment fragment = EditUserPhoneFragment.newInstance(R.id.container_activity_main_commerce_settings);
        goToFragment(fragment);
    }

    private void goToPasswordFragment() {
        Fragment fragment = EditUserPasswordFragment.newInstance();
        goToFragment(fragment);
    }

    private void goToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.container_activity_main_commerce_settings, fragment).
                addToBackStack(null).
                commit();
    }

    private void updateUser() {
        String name = mEdtName.getText().toString();
        String email = mEdtEmail.getText().toString();
        String phoneNumber = mEdtPhoneNumber.getText().toString();
        mPresenter.updateUser(CameraHelper.getImageEncoded(newUserImage), name, null, email, phoneNumber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button_edit_commerce_fragment:
                updateUser();
                break;
            case R.id.image_profile_view_edit_commerce_fragment :
                startActivityForResult(CameraHelper.getPickImageChooserIntent(getActivity()), 200);
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {
                case R.id.password_editText_edit_commerce_fragment:
                    goToPasswordFragment();
                    break;
                case R.id.phoneNumber_editText_edit_commerce_fragment :
                    goToPhoneFragment();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setErrorMessage(int component, int errorMessage) {
        EditText editText = rootView.findViewById(component);
        editText.setError(getString(errorMessage));
        editText.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, this.getContext());
    }


    @Override
    public void navigateToSettingsView() {
        super.getActivity().onBackPressed();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void changedImage(Bitmap bitmap) {
        newUserImage = bitmap;
        Uri uri = CameraHelper.getImageUri(getContext(), bitmap);
        Picasso.with(getContext()).load(uri).transform(new CropCircleTransformation()).into(mProfileImage);
    }
}
