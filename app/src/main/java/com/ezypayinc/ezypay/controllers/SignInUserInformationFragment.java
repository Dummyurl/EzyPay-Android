package com.ezypayinc.ezypay.controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.UserServiceClient;
import com.ezypayinc.ezypay.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInUserInformationFragment extends Fragment implements View.OnClickListener {

    private Button btnSaveUser;
    public OnChangeViewListener listener;
    private EditText edtName, edtLastname,edtPhonenumber, edtEmail,
            edtPassword;

    public SignInUserInformationFragment() {
        // Required empty public constructor
    }

    public static SignInUserInformationFragment newInstance() {
        SignInUserInformationFragment fragment = new SignInUserInformationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in_user_information, container, false);
        edtName = (EditText)rootView.findViewById(R.id.sig_in_name);
        edtLastname = (EditText)rootView.findViewById(R.id.sig_in_lastname);
        edtPhonenumber = (EditText)rootView.findViewById(R.id.sig_in_phone_number);
        edtEmail = (EditText)rootView.findViewById(R.id.sig_in_email);
        edtPassword = (EditText)rootView.findViewById(R.id.sign_in_password);
        btnSaveUser = (Button)rootView.findViewById(R.id.sign_in_save_user_information);
        btnSaveUser.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        Log.e("LLego aca","LLego aca");
        final User user = new User();
        user.mName = edtName.getText().toString();
        user.mLastname = edtLastname.getText().toString();
        user.mPhoneNumber = edtPhonenumber.getText().toString();
        user.mEmail = edtEmail.getText().toString();
        user.mPassword = edtPassword.getText().toString();

        UserServiceClient service = new UserServiceClient(getContext().getApplicationContext());
        try {
            service.registerUser(user, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("LLego aca","respuesta");
                    int userId = 0;
                    try {
                        userId = response.getInt("response");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SignInPaymentInformationFragment fragment = SignInPaymentInformationFragment.newInstance(userId);
                    listener.changeView(fragment);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error message", error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface  OnChangeViewListener {

        public void changeView(Fragment newFragment);
    }
}
