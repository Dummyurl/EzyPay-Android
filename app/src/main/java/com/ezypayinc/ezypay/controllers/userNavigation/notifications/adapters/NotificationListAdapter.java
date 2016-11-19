package com.ezypayinc.ezypay.controllers.userNavigation.notifications.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Notification;

/**
 * Created by gustavoquesada on 11/18/16.
 */

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

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView restaurantName, userName, notificationDate, notificationCost;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)  itemView.findViewById(R.id.notification_list_restaurant_image);
            restaurantName = (TextView) itemView.findViewById(R.id.notification_list_restaurant_name);
            userName = (TextView) itemView.findViewById(R.id.notification_list_user_name);
            notificationDate = (TextView) itemView.findViewById(R.id.notification_list_notification_date);
            notificationCost = (TextView) itemView.findViewById(R.id.notification_list_notification_cost);
        }
    }
}

