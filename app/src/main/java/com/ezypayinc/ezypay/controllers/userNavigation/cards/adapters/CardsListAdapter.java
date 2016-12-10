package com.ezypayinc.ezypay.controllers.userNavigation.cards.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Card;

/**
 * Created by Gustavo Quesada S on 07/12/2016.
 */

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.CardsViewHolder>{

    public interface OnItemClickListener {
        void onClickListener(Card card);
    }

    private Context mContext;
    private OnItemClickListener mListener;

    public CardsListAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards_list, parent, false);

        return new CardsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickListener(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CardsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCard;
        public ImageView imgCardIcon;

        public CardsViewHolder(View view){
            super(view);
            tvCard = (TextView) view.findViewById(R.id.cards_list_card_number);
            imgCardIcon = (ImageView) view.findViewById(R.id.cards_list_card_icon);
        }
    }
}
