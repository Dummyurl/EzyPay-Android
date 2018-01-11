package com.ezypayinc.ezypay.controllers.login.commerce;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInPhotoCommerceView;
import com.ezypayinc.ezypay.helpers.CameraHelper;
import com.ezypayinc.ezypay.presenter.LoginPresenters.commerce.ISignInPhotoCommercePresenter;
import com.ezypayinc.ezypay.presenter.LoginPresenters.commerce.SignInPhotoCommercePresenter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SignInPhotoCommerceFragment extends Fragment implements View.OnClickListener, OnChangeImageListener, ISignInPhotoCommerceView {

    private ImageView imageViewPhotoCommerce;
    private NumberPicker tablesNumberPicker;
    private Button btnNextButton;
    private Bitmap mCommerceImage;
    private static final int MAX_TABLES_VALUE = 101;
    private ISignInPhotoCommercePresenter mPresenter;

    public SignInPhotoCommerceFragment() {
        // Required empty public constructor
    }

    public static SignInPhotoCommerceFragment newInstance() {
        SignInPhotoCommerceFragment fragment = new SignInPhotoCommerceFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_sign_in_photo_commerce, container, false);
        imageViewPhotoCommerce = rootView.findViewById(R.id.image_view_sign_in_photo_commerce_fragment);
        tablesNumberPicker = rootView.findViewById(R.id.number_picker_table_quantity_photo_commerce_fragment);
        btnNextButton = rootView.findViewById(R.id.next_button_photo_commerce_fragment);
        btnNextButton.setOnClickListener(this);
        imageViewPhotoCommerce.setOnClickListener(this);
        populateTablesValues();
        mPresenter = new SignInPhotoCommercePresenter(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SignInCommerceMainActivity) context).setImageListener(this);
    }

    private void populateTablesValues() {
        tablesNumberPicker.setMinValue(0);
        tablesNumberPicker.setMaxValue(MAX_TABLES_VALUE);
        String []displayedValues = new String[MAX_TABLES_VALUE + 1];
        for (int i = 0; i  <= MAX_TABLES_VALUE; i++) {
            displayedValues[i] = String.valueOf(i);
        }
        tablesNumberPicker.setDisplayedValues(displayedValues);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_sign_in_photo_commerce_fragment :
                startActivityForResult(CameraHelper.getPickImageChooserIntent(getActivity()), 200);
                break;
            case R.id.next_button_photo_commerce_fragment :
                int tablesQuantity = tablesNumberPicker.getValue();
                mPresenter.saveCommerceData(mCommerceImage, tablesQuantity);
            default:
                break;
        }
    }

    @Override
    public void changedImage(Bitmap bitmap) {
        mCommerceImage = bitmap;
        Uri uri = CameraHelper.getImageUri(getContext(), bitmap);
        Picasso.with(getContext()).load(uri).transform(new CropCircleTransformation()).into(imageViewPhotoCommerce);
    }

    @Override
    public void showImageRequiredError() {
        DialogBuilder builder = new DialogBuilder(getContext());
        builder.defaultAlertDialog(getString(R.string.error_photo_field_required));
    }

    public void goToNextView() {
        Fragment fragment = SignInBankInformationFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.sign_in_commerce_container_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
