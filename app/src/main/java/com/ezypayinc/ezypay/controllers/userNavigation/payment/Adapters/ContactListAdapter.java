package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Friend;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactsViewHolder> {
    private List<Friend> mFriendsList;
    private OnItemClickListener mListener;
    private Context mContext;

    public ContactListAdapter(List<Friend> usersList, OnItemClickListener listener, Context context){
        mFriendsList = usersList;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_contact_list, parent, false);

        return new ContactsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        final Friend friend = mFriendsList.get(position);
        holder.tvName.setText(friend.getName() + " " + friend.getLastname());
        loadProfileImage(friend, holder.profileImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = holder.contactCheckedImageView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                boolean isChecked = visibility == View.VISIBLE;
                holder.contactCheckedImageView.setVisibility(visibility);
                mListener.OnItemClickListener(friend, isChecked);
            }
        });
        //setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
         return mFriendsList.size();
    }

    /*private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }*/

    private void loadProfileImage(Friend friend, ImageView imageView) {
        if(friend != null && friend.getAvatar() != null) {
            Picasso.with(mContext).load(friend.getAvatar()).transform(new CropCircleTransformation()).into(imageView);
        }
    }

    public void setFilter(List<Friend> filteredList) {
        mFriendsList = new ArrayList<>();
        mFriendsList.addAll(filteredList);
        notifyDataSetChanged();
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView profileImage, contactCheckedImageView;

        ContactsViewHolder(View view){
            super(view);
            tvName = (TextView) view.findViewById(R.id.payment_contact_list_name);
            profileImage = (ImageView) view.findViewById(R.id.payment_contact_list_profile_image);
            contactCheckedImageView = (ImageView) view.findViewById(R.id.contact_checked_contact_list_imageView);
        }
    }


    public interface OnItemClickListener {
        void OnItemClickListener(Friend friend, boolean isChecked);
    }
}
