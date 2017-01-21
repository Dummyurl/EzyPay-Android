package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.User;

import java.util.List;

/**
 * Created by gustavoquesada on 1/19/17.
 */

public class SplitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mUserList;
    private int index;
    private int indexHeaders;
    private String[] headerTitles;
    private int indexPayment;

    public SplitAdapter(List<User> userList, Context context) {
        mUserList = userList;
        headerTitles = context.getResources().getStringArray(R.array.split_fragment_header_titles);
        index = 0;
        indexHeaders = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (index == 0 || index == 2 || index == 5) {
            index ++;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else if(index > 5){
            index ++;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_payment_detail_split_fragment, parent, false);
            return new PaymentDetailViewHolder(itemView);
        } else {
            index ++;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_split_fragment, parent, false);
            return new SplitViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder view = (HeaderViewHolder) holder;
            view.headerTitleTextView.setText(headerTitles[indexHeaders]);
            indexHeaders ++;
        } else if (holder instanceof SplitViewHolder)  {
            SplitViewHolder view = (SplitViewHolder) holder;
            view.userNameTextView.setText("Gustavo Quesada Sánchez");
            view.paymentDetailTextView.setText("$1000");
        } else {
            PaymentDetailViewHolder view = (PaymentDetailViewHolder) holder;
            if(indexPayment == 0) {
                view.itemNameTextView.setText("Faltante");
                view.itemDetailTextView.setText("$0");
            } else  {
                view.itemNameTextView.setText("Total");
                view.itemDetailTextView.setText("$15000");
            }
            indexPayment ++;
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTitleTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextView = (TextView) itemView.findViewById(R.id.header_title_textView);
        }
    }

    public class SplitViewHolder extends RecyclerView.ViewHolder {
        public ImageView profilePhotoImageView;
        public TextView userNameTextView, paymentDetailTextView;
        public SeekBar paymentSeekBar;

        public SplitViewHolder(View itemView) {
            super(itemView);
            profilePhotoImageView = (ImageView) itemView.findViewById(R.id.split_fragment_imageView_profile_image);
            userNameTextView = (TextView) itemView.findViewById(R.id.split_fragment_textView_userName);
            paymentDetailTextView = (TextView) itemView.findViewById(R.id.split_fragment_textView_paymentDetail);
            paymentSeekBar = (SeekBar) itemView.findViewById(R.id.split_fragment_seekBar_payment);

        }
    }

    public class PaymentDetailViewHolder extends  RecyclerView.ViewHolder {
        public TextView itemNameTextView;
        public TextView itemDetailTextView;

        public PaymentDetailViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = (TextView) itemView.findViewById(R.id.item_name_payment_detail_textView);
            itemDetailTextView = (TextView) itemView.findViewById(R.id.item_detail_payment_detail_textView);
        }
    }

}
