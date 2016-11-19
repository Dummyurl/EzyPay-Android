package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavoquesada on 11/16/16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {
    private List<Contact> mContactsList;

    public ContactListAdapter(List<Contact> contactsList){
        mContactsList = contactsList;

    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_contact_list, parent, false);

        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {
        Contact contact = mContactsList.get(position);
        holder.tvName.setText(contact.getName());
        //setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
         return mContactsList.size();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    public void setFilter(List<Contact> filteredList) {
        mContactsList = new ArrayList<>();
        mContactsList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView profileImage;

        public ContactsViewHolder(View view){
            super(view);
            tvName = (TextView) view.findViewById(R.id.payment_contact_list_name);
            profileImage = (ImageView) view.findViewById(R.id.payment_contact_list_profile_image);
        }
    }
}
