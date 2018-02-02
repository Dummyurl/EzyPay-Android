package com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Friend> mFriendList;
    private Payment mPayment;
    private int index, indexHeaders, indexFriends;
    private User mUser;
    private String[] headerTitles;

    public PaymentAdapter(Payment payment, User user, Context context) {
        mContext = context;
        mPayment = payment;
        mFriendList = mPayment.getFriends();
        mUser = user;
        headerTitles = mContext.getResources().getStringArray(R.array.split_fragment_header_titles);
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
                    .inflate(R.layout.cell_payment_fragment, parent, false);
            index ++;
            return new PaymentViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder view = (HeaderViewHolder) holder;
            view.headerTitleTextView.setText(headerTitles[indexHeaders]);
            indexHeaders++;
        } else  {
            PaymentViewHolder view = (PaymentViewHolder) holder;
            if (position == 1) {
                setupUserCell(view);
            } else {
                setupFriendCell(view);
            }
        }
    }

    public void updateData(Payment payment) {
        index = 0;
        indexHeaders = 0;
        indexFriends = 0;
        int listSize = mFriendList.size() + 3;
        mPayment = null;
        mFriendList.clear();
        notifyItemRangeRemoved(0,listSize);

        mPayment = payment;
        mFriendList = payment.getFriends();
        notifyDataSetChanged();
    }

    private void setupUserCell(PaymentViewHolder view) {
        view.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        view.userNameTextView.setText(mUser.getName().concat(" ").concat(mUser.getLastName()));
        view.paymentDetailTextView.setText(mPayment.getCurrency().getCurrencySymbol() + " " + mPayment.getUserCost());
        view.paymentProgressBar.setVisibility(View.GONE);
        view.statusImageView.setVisibility(View.VISIBLE);
        getUserProfile(view.profilePhotoImageView, mUser.getAvatar());
    }

    private void setupFriendCell(PaymentViewHolder view) {
        Friend currentFriend = mFriendList.get(indexFriends);
        indexFriends++;
        view.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greenEzypayColor));
        view.userNameTextView.setText(currentFriend.getName().concat(" ").concat(currentFriend.getLastname()));
        view.paymentDetailTextView.setText(mPayment.getCurrency().getCurrencySymbol() + " " + currentFriend.getCost());
        view.paymentProgressBar.setVisibility(currentFriend.getState() == 1 ? View.GONE : View.VISIBLE);
        view.statusImageView.setVisibility(currentFriend.getState() == 1 ? View.VISIBLE : View.GONE);
        getUserProfile(view.profilePhotoImageView, currentFriend.getAvatar());
    }

    private void getUserProfile(ImageView imageView, String avatar) {
        Picasso.with(mContext).load(avatar).transform(new CropCircleTransformation()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mFriendList.size() + 3;
    }

    class PaymentViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePhotoImageView, statusImageView;
        TextView userNameTextView, paymentDetailTextView;
        ProgressBar paymentProgressBar;

        PaymentViewHolder(View itemView) {
            super(itemView);
            profilePhotoImageView = (ImageView) itemView.findViewById(R.id.payment_fragment_imageView_profile_image);
            statusImageView = (ImageView) itemView.findViewById(R.id.payment_fragment_imageView_status);
            userNameTextView = (TextView) itemView.findViewById(R.id.payment_fragment_textView_userName);
            paymentDetailTextView = (TextView) itemView.findViewById(R.id.payment_fragment_textView_paymentDetail);
            paymentProgressBar = (ProgressBar) itemView.findViewById(R.id.payment_fragment_cell_progressBar);
        }
    }
}


