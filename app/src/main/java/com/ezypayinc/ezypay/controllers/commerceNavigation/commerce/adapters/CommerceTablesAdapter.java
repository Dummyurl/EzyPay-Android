package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.model.Table;
import java.util.List;


public class CommerceTablesAdapter extends RecyclerView.Adapter<CommerceTablesAdapter.TableCellViewHolder> {

    private List<Table> mTables;
    private Context mContext;
    private OnItemClickListener mListener;

    public CommerceTablesAdapter(List<Table> tables, Context context, OnItemClickListener listener) {
        mTables = tables;
        mContext = context;
        mListener = listener;
    }

    @Override
    public TableCellViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_commerce_table, parent, false);
        return new TableCellViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(TableCellViewHolder holder, int position) {
        final Table table = mTables.get(position);
        if(table.isActive() == 1) {
            holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_gray_table));
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.circle_green_table));
        }
        holder.tableNumberTextView.setText(String.valueOf(table.getTableNumber()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnItemClickListener(table);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTables.size();
    }

    public void clear() {
        mTables.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Table> list) {
        mTables.addAll(list);
        notifyDataSetChanged();
    }

    class TableCellViewHolder extends RecyclerView.ViewHolder {
        private TextView tableNumberTextView;

        TableCellViewHolder(View view) {
            super(view);
            tableNumberTextView = view.findViewById(R.id.cell_commerce_table_table_number);
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(Table table);
    }
}
