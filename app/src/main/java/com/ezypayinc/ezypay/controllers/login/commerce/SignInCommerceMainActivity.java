package com.ezypayinc.ezypay.controllers.login.commerce;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.model.PhoneCode;

import java.io.File;
import java.io.IOException;

public class SignInCommerceMainActivity extends AppCompatActivity implements OnPhoneCodeSelected {

    private OnPhoneCodeSelected mPhoneSelected;
    private OnChangeImageListener mImageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_commerce_main);
        if(savedInstanceState == null) {
            SignInCommerceFragment fragment = SignInCommerceFragment.newInstance();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.sign_in_commerce_container_view, fragment);
            fragmentTransaction.commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneSelected.phoneCodeSelected(phoneCode);
    }

    public void setPhoneSelectedListener(OnPhoneCodeSelected listener) {
        mPhoneSelected = listener;
    }

    public void setImageListener(OnChangeImageListener listener) {
        mImageListener = listener;
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
