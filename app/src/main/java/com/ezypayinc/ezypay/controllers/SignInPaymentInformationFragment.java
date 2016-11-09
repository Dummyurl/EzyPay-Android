package com.ezypayinc.ezypay.controllers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.CardServiceClient;
import com.ezypayinc.ezypay.model.Card;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInPaymentInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInPaymentInformationFragment extends Fragment implements View.OnClickListener{

    private EditText edtCardnumber, edtCvv;
    private Spinner spnMonth, spnYear;
    private Button btnSaveCard;

    private static final String USER_ID = "user_id";
    private int mUserId;

    public SignInPaymentInformationFragment() {
        // Required empty public constructor
    }

    public static SignInPaymentInformationFragment newInstance(int userId) {
        SignInPaymentInformationFragment fragment = new SignInPaymentInformationFragment();
        Bundle args = new Bundle();
        args.putInt(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getInt(USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_in_payment_information, container, false);
        edtCardnumber = (EditText)rootView.findViewById(R.id.sign_in_card_number);
        edtCvv = (EditText)rootView.findViewById(R.id.sign_in_cvv);
        spnMonth = (Spinner) rootView.findViewById(R.id.sign_in_spn_month);
        spnYear = (Spinner) rootView.findViewById(R.id.sign_in_spn_year);
        btnSaveCard = (Button) rootView.findViewById(R.id.sign_in_save_card);
        btnSaveCard.setOnClickListener(this);
        populateSpinners();

        return rootView;
    }

    public void populateSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(adapter);
        populateYearSpinner();
    }

    public void populateYearSpinner() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= thisYear + 10; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        spnYear.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        Card card = new Card();
        card.setCardNumber(edtCardnumber.getText().toString());
        card.setCvv(Integer.valueOf(edtCvv.getText().toString()));
        card.getUser().setId(mUserId);
        card.setMonth(spnMonth.getSelectedItemPosition() + 1);
        card.setYear(Integer.valueOf(spnYear.getSelectedItem().toString()));

        CardServiceClient service = new CardServiceClient(getContext().getApplicationContext());
        try {
            service.createCard(card, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Success card", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error card", error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


