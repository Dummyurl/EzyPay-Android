package com.ezypayinc.ezypay.controllers.userNavigation.notifications.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationViewHolder>{

    public NotificationListAdapter(){

    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_notification_list, parent, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView restaurantName, notificationDate, notificationCost;

        NotificationViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)  itemView.findViewById(R.id.notification_list_restaurant_image);
            restaurantName = (TextView) itemView.findViewById(R.id.notification_list_restaurant_name);
            notificationDate = (TextView) itemView.findViewById(R.id.notification_list_notification_date);
            notificationCost = (TextView) itemView.findViewById(R.id.notification_list_notification_cost);
        }
    }
}

