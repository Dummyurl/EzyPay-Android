package com.ezypayinc.ezypay.controllers.userNavigation.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.interfaceViews.IEditUserPasswordView;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EditUserPasswordPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.IEditUserPasswordPresenter;

public class EditUserPasswordFragment extends Fragment implements IEditUserPasswordView, View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private IEditUserPasswordPresenter mPresenter;
    private EditText edtPassword;

    public EditUserPasswordFragment() {
        // Required empty public constructor
    }


    public static EditUserPasswordFragment newInstance() {
        EditUserPasswordFragment fragment = new EditUserPasswordFragment();
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
        View viewRoot = inflater.inflate(R.layout.fragment_edit_user_password, container, false);
        edtPassword = (EditText) viewRoot.findViewById(R.id.password_editText_edit_user_password_fragment);
        Button saveAction = (Button) viewRoot.findViewById(R.id.save_button_edit_user_password_fragment);
        mPresenter = new EditUserPasswordPresenter(this);
        saveAction.setOnClickListener(this);

        return viewRoot;
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.edit_password_view_title);
        setupProgressDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void goBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void showProgressDialog() {
        if(mProgressDialog == null)
        {
            setupProgressDialog();
        }
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void onClick(View v) {
        String password = edtPassword.getText().toString();
        mPresenter.updatePassword(password);
    }
}
