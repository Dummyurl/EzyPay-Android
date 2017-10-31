package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;


class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView headerTitleTextView;

    HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitleTextView = (TextView) itemView.findViewById(R.id.header_title_textView);
    }
}
