package com.ezypayinc.ezypay.controllers.login;


import android.os.Bundle;
import android.support.annotation.IntegerRes;
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
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.login.interfaceViews.SignInPaymentInformationView;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.ISignInPaymentInformationPresenter;
import com.ezypayinc.ezypay.presenter.SignInPaymentInformationPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInPaymentInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInPaymentInformationFragment extends Fragment implements View.OnClickListener, SignInPaymentInformationView{

    private EditText edtCardnumber, edtCvv;
    private Spinner spnMonth, spnYear;
    private Button btnSaveCard;
    private View mRootView;
    private OnFinishWizard listener;
    private ISignInPaymentInformationPresenter presenter;

    public SignInPaymentInformationFragment() {
        // Required empty public constructor
    }

    public static SignInPaymentInformationFragment newInstance() {
        SignInPaymentInformationFragment fragment = new SignInPaymentInformationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_sign_in_payment_information, container, false);
        initComponents(rootView);
        presenter = new SignInPaymentInformationPresenter(this);
        presenter.populateSpinners();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        listener = (SignInPaymentInformationFragment.OnFinishWizard) getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.listener = null;
        this.presenter.onDestroy();
        this.presenter = null;
    }

    public void initComponents(View rootView) {
        mRootView = rootView;
        edtCardnumber = (EditText)rootView.findViewById(R.id.sign_in_card_number);
        edtCvv = (EditText)rootView.findViewById(R.id.sign_in_cvv);
        spnMonth = (Spinner) rootView.findViewById(R.id.sign_in_spn_month);
        spnYear = (Spinner) rootView.findViewById(R.id.sign_in_spn_year);
        btnSaveCard = (Button) rootView.findViewById(R.id.sign_in_save_card);
        btnSaveCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cardNumber = edtCardnumber.getText().toString();
        String cvvString = edtCvv.getText().toString();
        int month = spnMonth.getSelectedItemPosition() + 1;
        int year = Integer.valueOf(spnYear.getSelectedItem().toString());
        if(presenter.validateFields(cardNumber, cvvString)) {
            presenter.registerRecord(cardNumber, Integer.valueOf(cvvString), month, year);
        }
    }

    @Override
    public void setErrorMessage(int component, int error) {
        EditText view = (EditText) mRootView.findViewById(component);
        view.setError(getString(error));
        view.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getActivity());
    }

    @Override
    public void populateYearSpinner(ArrayList<String> years) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        spnYear.setAdapter(adapter);
    }

    @Override
    public void populateMonthSpinner(int months) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(adapter);
    }

    @Override
    public void navigateToHome() {
        listener.onFinishWizard();
    }


    public interface OnFinishWizard {
        void onFinishWizard();
    }
}


