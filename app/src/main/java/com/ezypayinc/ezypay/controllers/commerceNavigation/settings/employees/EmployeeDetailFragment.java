package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews.IEmployeeDetailView;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters.EmployeeDetailPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters.IEmployeeDetailPresenter;

public class EmployeeDetailFragment extends Fragment implements View.OnClickListener, IEmployeeDetailView {

    private EditText mNameEditText, mLastNameEditText, mUsernameEditText, mPasswordEditText;
    private Button mSaveButton;
    private int mViewType, mEmployeeId;
    private ProgressDialog mProgressDialog;
    private IEmployeeDetailPresenter mPresenter;

    private static final String EMPLOYEE_ID = "EMPLOYEE_ID";
    private static final String VIEW_TYPE = "VIEW_TYPE";


    public EmployeeDetailFragment() {
        // Required empty public constructor
    }

    public static EmployeeDetailFragment newInstance(int employeeId, int viewType) {
        EmployeeDetailFragment fragment = new EmployeeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(EMPLOYEE_ID, employeeId);
        args.putInt(VIEW_TYPE, viewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mEmployeeId = getArguments().getInt(EMPLOYEE_ID);
            mViewType = getArguments().getInt(VIEW_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        initComponents(rootView);
        return rootView;
    }

    private void initComponents(View rootView) {
        mNameEditText = rootView.findViewById(R.id.name_editText_edit_employee_fragment);
        mLastNameEditText = rootView.findViewById(R.id.lastName_editText_edit_employee_fragment);
        mUsernameEditText = rootView.findViewById(R.id.username_editText_edit_employee_fragment);
        mPasswordEditText = rootView.findViewById(R.id.password_editText_edit_employee_fragment);
        mSaveButton = rootView.findViewById(R.id.save_button_edit_employee_fragment);
        mSaveButton.setOnClickListener(this);
        mPresenter = new EmployeeDetailPresenter(this);
        setupView();
    }

    private void setupView() {
        if(mViewType == EmployeeDetailViewType.VIEW_EMPLOYEE.getType()) {
            setupViewEmployee();
            mPresenter.getEmployeeDetail(mEmployeeId);
        } else if(mViewType == EmployeeDetailViewType.ADD_EMPLOYEE.getType()) {
            setupAddView();
        } else {
            setupEditableView();
        }
    }

    private void setupViewEmployee() {
        getActivity().setTitle(R.string.title_view_employee_fragment);
        setupComponents(false);
    }

    private void setupAddView() {
        getActivity().setTitle(R.string.title_add_employee_fragment);
        mPasswordEditText.setText(null);
        setupComponents(true);
    }

    private void setupEditableView() {
        getActivity().setTitle(R.string.title_edit_employee_fragment);
        mPasswordEditText.setText(null);
        setupComponents(true);
    }

    private void setupComponents(boolean editable) {
        mNameEditText.setEnabled(editable);
        mLastNameEditText.setEnabled(editable);
        mUsernameEditText.setEnabled(editable);
        mPasswordEditText.setEnabled(editable);
        mSaveButton.setVisibility(editable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {

    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
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
    public void setupEmployee(User user) {

    }

    @Override
    public void goToEmployeeList() {

    }

    @Override
    public void onNetworkError(Object error) {

    }
}
