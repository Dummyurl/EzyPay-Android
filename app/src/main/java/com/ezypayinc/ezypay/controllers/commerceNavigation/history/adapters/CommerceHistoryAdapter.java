package com.ezypayinc.ezypay.controllers.commerceNavigation.history.adapters;


import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.SectionOrRow;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.HeaderViewHolder;
import com.ezypayinc.ezypay.model.CommerceHistory;
import com.ezypayinc.ezypay.model.HistoryDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommerceHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommerceHistory> mCommerceHistoryList;
    private List<HistoryDate> mDateList;
    private List<SectionOrRow> mSourceList;
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

    public CommerceHistoryAdapter(List<CommerceHistory> commerceHistoryList, List<HistoryDate> datesList) {
        mCommerceHistoryList = commerceHistoryList;
        mDateList = datesList;
        mSourceList = new ArrayList<>();
        parseData();
    }

    private void parseData() {
        List<CommerceHistory> copy = new ArrayList<>(mCommerceHistoryList);
        for (HistoryDate historyDate : mDateList) {
            Date date = getDateFromString(historyDate.getPaymentDate());
            mSourceList.add(SectionOrRow.createHeader(getDateName(date)));
            List<CommerceHistory> iterativeList = new ArrayList<>(copy);
            for (CommerceHistory history: iterativeList) {
                if(history.getPaymentDate().equalsIgnoreCase(historyDate.getPaymentDate())) {
                    mSourceList.add(SectionOrRow.createRow(history));
                    copy.remove(history);
                }
            }
        }
    }

    public int getItemViewType(int position) {
        super.getItemViewType(position);
        SectionOrRow sectionOrRow = mSourceList.get(position);
        return sectionOrRow.isRow() ? 1 : 0;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_commerce_history, parent, false);

            return new CommerceHistoryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionOrRow sectionOrRow = mSourceList.get(position);
        if(sectionOrRow.isRow()) {
            CommerceHistory commerceHistory = (CommerceHistory) sectionOrRow.getRow();
            CommerceHistoryViewHolder historyHolder = (CommerceHistoryViewHolder) holder;
            historyHolder.mUserNameTextView.setText(commerceHistory.getCustomerFullName());
            historyHolder.mPaymentCostTextView.setText(getCurrencySymbol(commerceHistory.getCode()) + " " + commerceHistory.getCost());
            if(commerceHistory.getEmployeeFullName() != null) {
                historyHolder.mEmployeeTextView.setVisibility(View.VISIBLE);
                historyHolder.mEmployeeTextView.setText(commerceHistory.getEmployeeFullName());
            } else {
                historyHolder.mEmployeeTextView.setVisibility(View.GONE);
            }

        } else {
            HeaderViewHolder header = (HeaderViewHolder) holder;
            header.headerTitleTextView.setText(sectionOrRow.getHeader());
        }
    }

    @Override
    public int getItemCount() {
        return mSourceList.size();
    }

    private Date getDateFromString(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_STRING);
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private String getDateName(Date date) {
        if(date != null) {
            String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
            String day = (String) DateFormat.format("dd", date);
            String monthString = (String) DateFormat.format("MMM", date);
            String year = (String) DateFormat.format("yyyy", date);

            return dayOfTheWeek + ", " + monthString + " " + day + ", " + year;
        }
        return null;
    }

    private String getCurrencySymbol(String code) {
        java.util.Currency currency = java.util.Currency.getInstance(code);
        return currency.getSymbol();
    }

    class CommerceHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView mUserNameTextView;
        TextView mPaymentCostTextView;
        TextView mEmployeeTextView;

        CommerceHistoryViewHolder(View view) {
            super(view);
            mUserNameTextView = view.findViewById(R.id.cell_commerce_history_userName);
            mPaymentCostTextView = view.findViewById(R.id.cell_commerce_history_cost);
            mEmployeeTextView = view.findViewById(R.id.cell_commerce_history_employee);
        }
    }
}
