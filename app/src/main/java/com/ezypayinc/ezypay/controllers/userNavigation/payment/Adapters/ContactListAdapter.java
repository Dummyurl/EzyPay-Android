package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavoquesada on 11/16/16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {
    private List<User> mUsersList;
    private OnItemClickListener mListener;

    public ContactListAdapter(List<User> usersList, OnItemClickListener listener){
        mUsersList = usersList;
        mListener = listener;

    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_contact_list, parent, false);

        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        final User user = mUsersList.get(position);
        holder.tvName.setText(user.getName() + " " + user.getLastName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = holder.contactCheckedImageView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                boolean isChecked = visibility == View.VISIBLE ? true: false;
                holder.contactCheckedImageView.setVisibility(visibility);
                mListener.OnItemClickListener(user, isChecked);
            }
        });
        //setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
         return mUsersList.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    public void setFilter(List<User> filteredList) {
        mUsersList = new ArrayList<>();
        mUsersList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView profileImage, contactCheckedImageView;

        public ContactsViewHolder(View view){
            super(view);
            tvName = (TextView) view.findViewById(R.id.payment_contact_list_name);
            profileImage = (ImageView) view.findViewById(R.id.payment_contact_list_profile_image);
            contactCheckedImageView = (ImageView) view.findViewById(R.id.contact_checked_contact_list_imageView);
        }
    }


    public interface OnItemClickListener {
        void OnItemClickListener(User user, boolean isChecked);
    }
}
