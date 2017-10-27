package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SplitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Friend> mFriendsList;
    private User mUser;
    private int index, indexHeaders, indexFriends;
    private String[] headerTitles;
    private int indexPayment;

    public SplitAdapter(List<Friend> friendList, User user, Context context) {
        mFriendsList = friendList;
        mUser = user;
        headerTitles = context.getResources().getStringArray(R.array.split_fragment_header_titles);
        index = 0;
        indexHeaders = 0;
        indexFriends = 0;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (index == 0 || index == 2) {
            index ++;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell_split_fragment, parent, false);
            index ++;
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
            if (position == 1) {
                view.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                view.userNameTextView.setText(mUser.getName().concat(" ").concat(mUser.getLastName()));
                view.paymentDetailTextView.setText("$0");
                getUserProfile(view.profilePhotoImageView, mUser.getAvatar());
            } else {
                Friend currentFriend = mFriendsList.get(indexFriends);
                indexFriends++;
                view.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greenEzypayColor));
                view.userNameTextView.setText(currentFriend.getName().concat(" ").concat(currentFriend.getLastname()));
                view.paymentDetailTextView.setText("$0");
                getUserProfile(view.profilePhotoImageView, currentFriend.getAvatar());
            }

        } else {
            PaymentDetailViewHolder view = (PaymentDetailViewHolder) holder;
            if(indexPayment == 0) {
                view.itemNameTextView.setText("Faltante");
                view.itemDetailTextView.setText("$12000");
            } else  {
                view.itemNameTextView.setText("Total");
                view.itemDetailTextView.setText("$15000");
            }
            indexPayment ++;
        }
    }

    public void getUserProfile(ImageView imageView, String avatar) {
        Picasso.with(mContext).load(avatar).transform(new CropCircleTransformation()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mFriendsList.size() + 3;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextView = (TextView) itemView.findViewById(R.id.header_title_textView);
        }
    }

    private class SplitViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhotoImageView;
        TextView userNameTextView, paymentDetailTextView;
        SeekBar paymentSeekBar;

        SplitViewHolder(View itemView) {
            super(itemView);
            profilePhotoImageView = (ImageView) itemView.findViewById(R.id.split_fragment_imageView_profile_image);
            userNameTextView = (TextView) itemView.findViewById(R.id.split_fragment_textView_userName);
            paymentDetailTextView = (TextView) itemView.findViewById(R.id.split_fragment_textView_paymentDetail);
            paymentSeekBar = (SeekBar) itemView.findViewById(R.id.split_fragment_seekBar_payment);
            paymentSeekBar.setPadding(10,0,0,0);

        }
    }

    private class PaymentDetailViewHolder extends  RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemDetailTextView;

        PaymentDetailViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = (TextView) itemView.findViewById(R.id.item_name_payment_detail_textView);
            itemDetailTextView = (TextView) itemView.findViewById(R.id.item_detail_payment_detail_textView);
        }
    }

}
