package com.ezypayinc.ezypay.controllers.userNavigation.history.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.Helpers.SectionOrRow;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.HeaderViewHolder;
import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.UserHistory;
import com.squareup.picasso.Picasso;

import android.text.format.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UserHistory> mUserHistory;
    private List<HistoryDate> mDatesList;
    private List<SectionOrRow> mSourceList;
    private static final String IMAGE_ENDPOINT = "https://storage.googleapis.com/ugwo-contact-pictures/";
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";

    public HistoryListAdapter(List<UserHistory> userHistory, List<HistoryDate> datesList, Context context) {
        mUserHistory = userHistory;
        mContext = context;
        mDatesList = datesList;
        mSourceList = new ArrayList<>();
        parseData();
    }

    private void parseData() {
        List<UserHistory> copy = new ArrayList<>(mUserHistory);
        for (HistoryDate historyDate : mDatesList) {
            Date date = getDateFromString(historyDate.getPaymentDate());
            mSourceList.add(SectionOrRow.createHeader(getDateName(date)));
            List<UserHistory> iterativeList = new ArrayList<>(copy);
            for (UserHistory history: iterativeList) {
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
                    .inflate(R.layout.cell_user_history_list, parent, false);

            return new HistoryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionOrRow sectionOrRow = mSourceList.get(position);
        if(sectionOrRow.isRow()) {
            UserHistory userHistory = (UserHistory) sectionOrRow.getRow();
            HistoryViewHolder historyHolder = (HistoryViewHolder) holder;
            historyHolder.commerceName.setText(userHistory.getName());
            historyHolder.paymentCost.setText(getCurrencySymbol(userHistory.getCode()) + " " + userHistory.getCost());
            getImage(historyHolder.commerceImage, userHistory.getAvatar());
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

    private void getImage(ImageView imageView, String avatar) {
        if(avatar != null) {
            String url = IMAGE_ENDPOINT + avatar;
            Picasso.with(mContext).load(url).into(imageView);
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView commerceImage;
        private TextView commerceName;
        private  TextView paymentCost;

        HistoryViewHolder(View itemView) {
            super(itemView);
            commerceImage = (ImageView) itemView.findViewById(R.id.cell_user_history_commerce_image);
            commerceName = (TextView) itemView.findViewById(R.id.cell_user_history_commerce_name);
            paymentCost = (TextView) itemView.findViewById(R.id.cell_user_history_payment_cost);
        }
    }

}
