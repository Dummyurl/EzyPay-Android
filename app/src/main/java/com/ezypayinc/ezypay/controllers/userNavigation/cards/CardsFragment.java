package com.ezypayinc.ezypay.controllers.userNavigation.cards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.cards.adapters.CardsListAdapter;

public class CardsFragment extends Fragment {
    private RecyclerView cardListRecyclerView;
    private CardsListAdapter adapter;

    public CardsFragment() {
        // Required empty public constructor
    }

    public static CardsFragment newInstance() {
        CardsFragment fragment = new CardsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        cardListRecyclerView = (RecyclerView) rootView.findViewById(R.id.cards_list_recycler_view);
        adapter = new CardsListAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        cardListRecyclerView.setLayoutManager(mLayoutManager);
        cardListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardListRecyclerView.setAdapter(adapter);
        return rootView;
    }
}
