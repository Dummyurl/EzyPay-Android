package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.adapters.EmployeeListAdapter;
import com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.interfaceViews.IEmployeeListView;
import com.ezypayinc.ezypay.model.User;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters.EmployeeListPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.EmployeePresenters.IEmployeeListPresenter;

import java.util.ArrayList;
import java.util.List;


public class EmployeeListFragment extends Fragment implements IEmployeeListView {

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private EmployeeListAdapter mAdapter;
    private IEmployeeListPresenter mPresenter;

    public EmployeeListFragment() {
        // Required empty public constructor
    }
    public static EmployeeListFragment newInstance() {
        EmployeeListFragment fragment = new EmployeeListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_employee_list, container, false);
        mRecyclerView = rootView.findViewById(R.id.employee_list_fragment_recycler_view);
        mAdapter = new EmployeeListAdapter(new ArrayList<User>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new EmployeeListPresenter(this);
        mPresenter.getEmployeeList();
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_employee_fragment);
        return rootView;
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
    public void populateEmployeeList(List<User> employeeList) {
        mAdapter.clear();
        mAdapter.addAll(employeeList);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.cards_view_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId() == R.id.add_card_item) {
            Fragment fragment = EmployeeDetailFragment.newInstance(0, EmployeeDetailViewType.ADD_EMPLOYEE.getType());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.container_activity_main_employee, fragment).
                    addToBackStack(null).
                    commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
