package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.User;

import java.util.List;

/**
 * Created by gustavoquesada on 2/22/17.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private List<User> mFriendList;

    public PaymentAdapter(List<User> friendList) {
        mFriendList = friendList;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_payment_fragment, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        holder.paymentDetailTextView.setText("$4000");
        holder.userNameTextView.setText("Gustavo Quesada");
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhotoImageView, statusImageView;
        TextView userNameTextView, paymentDetailTextView;

        PaymentViewHolder(View itemView) {
            super(itemView);
            profilePhotoImageView = (ImageView) itemView.findViewById(R.id.payment_fragment_imageView_profile_image);
            statusImageView = (ImageView) itemView.findViewById(R.id.payment_fragment_imageView_status);
            userNameTextView = (TextView) itemView.findViewById(R.id.payment_fragment_textView_userName);
            paymentDetailTextView = (TextView) itemView.findViewById(R.id.payment_fragment_textView_paymentDetail);
        }
    }
}
