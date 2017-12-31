package com.ezypayinc.ezypay.helpers;

import com.ezypayinc.ezypay.controllers.Helpers.OnChangeImageListener;
import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;

public interface ISettingsActivity {

    void setPhoneSelectedListener(OnPhoneCodeSelected listener);
    void setImageListener(OnChangeImageListener listener);
}
