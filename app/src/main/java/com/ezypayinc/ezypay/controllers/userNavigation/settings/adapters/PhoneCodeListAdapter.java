package com.ezypayinc.ezypay.controllers.userNavigation.settings.adapters;

import android.support.v7.widget.RecyclerView;

import com.ezypayinc.ezypay.controllers.Helpers.OnPhoneCodeSelected;
import com.ezypayinc.ezypay.model.PhoneCode;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup;
import com.ezypayinc.ezypay.R;
import android.view.View;
import java.util.List;

public class PhoneCodeListAdapter extends RecyclerView.Adapter<PhoneCodeListAdapter.PhoneCodeViewHolder> {
    private List<PhoneCode> mPhoneCodeList;
    private OnPhoneCodeSelected mPhoneCodeSelected;

    public PhoneCodeListAdapter(List<PhoneCode> phoneCodeList, OnPhoneCodeSelected phoneCodeSelected) {
        mPhoneCodeList = phoneCodeList;
        mPhoneCodeSelected = phoneCodeSelected;
    }

    @Override
    public PhoneCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_phone_code_list, parent, false);

        return new PhoneCodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhoneCodeViewHolder holder, int position) {
        final PhoneCode code = mPhoneCodeList.get(position);
        holder.codeLabel.setText(code.getPhonecode());
        holder.countryLabel.setText(code.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneCodeSelected.phoneCodeSelected(code);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPhoneCodeList.size();
    }

    class PhoneCodeViewHolder extends RecyclerView.ViewHolder {
        TextView codeLabel, countryLabel;
        ImageView selectedImageView;

        PhoneCodeViewHolder(View view) {
            super(view);
            codeLabel = (TextView) view.findViewById(R.id.code_cell_phone_code_list);
            countryLabel = (TextView) view.findViewById(R.id.country_cell_phone_code_list);
            selectedImageView = (ImageView) view.findViewById(R.id.selected_image_cell_phone_code_list);
        }
    }

}
