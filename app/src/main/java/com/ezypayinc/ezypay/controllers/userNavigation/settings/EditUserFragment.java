package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
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
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditUserView;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EditUserPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.IEditUserPresenter;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EditUserFragment extends Fragment implements IEditUserView, View.OnClickListener, View.OnFocusChangeListener, OnChangeImageListener {
    private EditText mEdtName, mEdtLastName, mEdtEmail, mEdtPhoneNumber, mEdtPassword;
    private ImageView mProfileImage;
    private View rootView;
    private ProgressDialog mProgressDialog;
    private IEditUserPresenter presenter;
    private Bitmap newUserImage;

    public EditUserFragment() {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance() {
        EditUserFragment fragment = new EditUserFragment();
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
        rootView = inflater.inflate(R.layout.fragment_edit_user, container, false);
        presenter = new EditUserPresenter(this);
        initUIComponents();
        getUser();
        setupProgressDialog();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SettingsMainActivity) context).setImageListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    private void initUIComponents() {
        mEdtName = (EditText) rootView.findViewById(R.id.name_editText_edit_user_fragment);
        mEdtLastName = (EditText) rootView.findViewById(R.id.lastName_editText_edit_user_fragment);
        mEdtEmail = (EditText) rootView.findViewById(R.id.email_editText_edit_user_fragment);
        mEdtPhoneNumber = (EditText) rootView.findViewById(R.id.phoneNumber_editText_edit_user_fragment);
        mEdtPassword = (EditText) rootView.findViewById(R.id.password_editText_edit_user_fragment);
        mProfileImage = (ImageView) rootView.findViewById(R.id.image_profile_view_edit_user_fragment);
        Button mBtnSave = (Button) rootView.findViewById(R.id.save_button_edit_user_fragment);
        mBtnSave.setOnClickListener(this);
        mProfileImage.setOnClickListener(this);
        mEdtPassword.setOnFocusChangeListener(this);
        mEdtPhoneNumber.setOnFocusChangeListener(this);
    }

    private void getUser() {
        User user = UserSingleton.getInstance().getUser();
        mEdtName.setText(user.getName());
        mEdtLastName.setText(user.getLastName());
        mEdtEmail.setText(user.getEmail());
        mEdtPhoneNumber.setText(user.getPhoneNumber());
        getImage(user);
    }

    private void getImage(User user) {
        Picasso.with(getContext()).load(user.getAvatar()).transform(new CropCircleTransformation()).into(mProfileImage);
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void setErrorMessage(int component, int errorMessage) {
        EditText editText = (EditText) rootView.findViewById(component);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button_edit_user_fragment:
                updateUser();
                break;
            case R.id.image_profile_view_edit_user_fragment :
                startActivityForResult(getPickImageChooserIntent(), 200);
            default:
                break;
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            switch (v.getId()) {
                case R.id.password_editText_edit_user_fragment:
                    goToPasswordFragment();
                    break;
                case R.id.phoneNumber_editText_edit_user_fragment :
                    goToPhoneFragment();
                    break;
                default:
                    break;
            }
        }
    }

    private void goToPhoneFragment() {
        Fragment fragment = EditUserPhoneFragment.newInstance();
        goToFragment(fragment);
    }

    private void goToPasswordFragment() {
        Fragment fragment = EditUserPasswordFragment.newInstance();
        goToFragment(fragment);
    }

    private void goToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.settings_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    private void updateUser() {
        String name = mEdtName.getText().toString();
        String lastName = mEdtLastName.getText().toString();
        String email = mEdtEmail.getText().toString();
        String phoneNumber = mEdtPhoneNumber.getText().toString();
        //presenter.updateUser(name, lastName, email, phoneNumber);
        presenter.uploadImage(getImageEncoded());
    }

    private Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getActivity().getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getActivity().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @Override
    public void changedImage(Bitmap bitmap) {
        newUserImage = bitmap;
        Uri uri = getImageUri(getContext(), bitmap);
        Picasso.with(getContext()).load(uri).transform(new CropCircleTransformation()).into(mProfileImage);
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private byte[] getImageEncoded() {
        if(newUserImage != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            newUserImage.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }
}
