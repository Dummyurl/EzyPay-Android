package com.ezypayinc.ezypay.controllers.Helpers;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpDateValidatorTextWatcher implements TextWatcher {

    private EditText mEditText;
    private String mLastInput;

    public ExpDateValidatorTextWatcher(EditText editText) {
        mEditText = editText;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String input = s.toString();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yy", Locale.GERMANY);
        Calendar expiryDateDate = Calendar.getInstance();
        try {
            expiryDateDate.setTime(formatter.parse(input));
        } catch (ParseException e) {
            if (s.length() == 2 && !mLastInput.endsWith("/")) {
                int month = Integer.parseInt(input);
                if (month <= 12) {
                    mEditText.setText(mEditText.getText().toString() + "/");
                    mEditText.setSelection(mEditText.getText().toString().length());
                } else {
                    String text = input.substring(0, 1);
                    mEditText.setText(text);
                    mEditText.setSelection(mEditText.getText().toString().length());
                }
            } else if (s.length() == 2 && mLastInput.endsWith("/")) {
                int month = Integer.parseInt(input);
                if (month <= 12) {
                    mEditText.setText(mEditText.getText().toString().substring(0, 1));
                    mEditText.setSelection(mEditText.getText().toString().length());
                } else {
                    mEditText.setText("");
                    mEditText.setSelection(mEditText.getText().toString().length());
                    //Toast.makeText(getApplicationContext(), "Enter a valid month", Toast.LENGTH_LONG).show();
                }
            } else if (s.length() == 1) {
                int month = Integer.parseInt(input);
                if (month > 1) {
                    mEditText.setText("0" + mEditText.getText().toString() + "/");
                    mEditText.setSelection(mEditText.getText().toString().length());
                }
            } else {

            }
            mLastInput = mEditText.getText().toString();
            return;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
