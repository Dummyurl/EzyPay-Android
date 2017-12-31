package com.ezypayinc.ezypay.controllers.commerceNavigation.settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.EditUserFragment;
import com.ezypayinc.ezypay.helpers.ISettingsActivity;
import com.ezypayinc.ezypay.model.PhoneCode;

import java.io.File;
import java.io.IOException;

public class MainCommerceSettingsActivity extends AppCompatActivity implements ISettingsActivity, OnPhoneCodeSelected {
    private OnChangeImageListener mImageListener;
    private OnPhoneCodeSelected mPhoneSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commerce_settings);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_activity_main_commerce_settings, EditCommerceFragment.newInstance())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
    }

    @Override
    public void setPhoneSelectedListener(OnPhoneCodeSelected listener) {
        mPhoneSelected = listener;
    }

    public void setImageListener(OnChangeImageListener listener) {
        mImageListener = listener;
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneSelected.phoneCodeSelected(phoneCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri pickUri = getPickImageResultUri(data);
            Bitmap bitmap = null;
            if(pickUri != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), pickUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
            }
            if(bitmap != null && mImageListener != null) {
                mImageListener.changedImage(bitmap);
            }
        }
    }

    private Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }
}
