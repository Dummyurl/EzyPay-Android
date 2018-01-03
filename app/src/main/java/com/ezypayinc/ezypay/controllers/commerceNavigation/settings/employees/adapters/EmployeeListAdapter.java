package com.ezypayinc.ezypay.controllers.commerceNavigation.settings.employees.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.User;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeCellViewHolder> {

    private List<User> mEmployeeList;

    public EmployeeListAdapter(List<User> employeeList) {
        mEmployeeList = employeeList;
    }

    @Override
    public EmployeeCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_employee_list, parent, false);
        return new EmployeeCellViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(EmployeeCellViewHolder holder, int position) {
        User user = mEmployeeList.get(position);
        holder.employeeNameTextView.setText(user.getFullName());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    public void clear() {
        mEmployeeList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<User> list) {
        mEmployeeList.addAll(list);
        notifyDataSetChanged();
    }

    class EmployeeCellViewHolder extends RecyclerView.ViewHolder {
        TextView employeeNameTextView;

        EmployeeCellViewHolder(View view) {
            super(view);
            employeeNameTextView = view.findViewById(R.id.cell_employee_list_employee_name);
        }
    }
}
