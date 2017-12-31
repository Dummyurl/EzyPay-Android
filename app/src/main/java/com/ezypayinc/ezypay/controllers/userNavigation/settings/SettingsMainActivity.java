package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.EzyPayApplication;
import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.helpers.ISettingsActivity;
import com.ezypayinc.ezypay.model.PhoneCode;

import java.io.File;
import java.io.IOException;

public class SettingsMainActivity extends AppCompatActivity implements OnPhoneCodeSelected, ISettingsActivity {

    private OnChangeImageListener mImageListener;
    private OnPhoneCodeSelected mPhoneSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.settings_main_container, EditUserFragment.newInstance())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((EzyPayApplication)getApplication()).setCurrentActivity(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setImageListener(OnChangeImageListener listener) {
        mImageListener = listener;
    }

    public void setPhoneSelectedListener(OnPhoneCodeSelected listener) {
        mPhoneSelected = listener;
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

    @Override
    public void phoneCodeSelected(PhoneCode phoneCode) {
        mPhoneSelected.phoneCodeSelected(phoneCode);
    }
}
