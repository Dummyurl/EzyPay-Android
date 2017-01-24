package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Card;

import java.util.List;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.CardsViewHolder>{

    public interface OnItemClickListener {
        void onClickListener(Card card);
    }

    private Context mContext;
    private List<Card> mCardList;
    private OnItemClickListener mListener;

    public CardsListAdapter(Context context, OnItemClickListener listener, List<Card> cardList) {
        mContext = context;
        mListener = listener;
        mCardList = cardList;

    }

    @Override
    public CardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_cards_list, parent, false);

        return new CardsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardsViewHolder holder, int position) {
        final Card card = mCardList.get(position);
        holder.tvCard.setText(card.getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickListener(card);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    class CardsViewHolder extends RecyclerView.ViewHolder {
        TextView tvCard;
        ImageView imgCardIcon;

        CardsViewHolder(View view){
            super(view);
            tvCard = (TextView) view.findViewById(R.id.cards_list_card_number);
            imgCardIcon = (ImageView) view.findViewById(R.id.cards_list_card_icon);
        }
    }
}
