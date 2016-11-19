package com.ezypayinc.ezypay.controllers.userNavigation.notifications.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;

/**
 * Created by gustavoquesada on 11/18/16.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {


    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_notification_history_list, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
