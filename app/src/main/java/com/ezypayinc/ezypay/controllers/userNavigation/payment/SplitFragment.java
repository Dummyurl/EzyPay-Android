package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.SplitAdapter;
import com.ezypayinc.ezypay.model.User;

import java.util.ArrayList;
import java.util.List;

public class SplitFragment extends Fragment implements View.OnClickListener {

    private static final String FRIENDS_KEY  = "FRIENDS_KEY";
    private List<User> friends;
    private Button btnNext;

    public SplitFragment() {
        // Required empty public constructor
    }

    public static SplitFragment newInstance(ArrayList<User> friends) {
        SplitFragment fragment = new SplitFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FRIENDS_KEY, friends);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            friends = getArguments().getParcelableArrayList(FRIENDS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_split, container, false);
        btnNext = (Button)  rootView.findViewById(R.id.split_fragment_next_button);
        btnNext.setOnClickListener(this);
        RecyclerView usersRecyclerView = (RecyclerView) rootView.findViewById(R.id.split_fragment_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        usersRecyclerView.setLayoutManager(mLayoutManager);
        usersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SplitAdapter mAdapter = new SplitAdapter(friends, UserSingleton.getInstance().getUser(), getContext());
        usersRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.split_fragment_next_button :
                Fragment fragment = PaymentFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.payment_main_container, fragment).
                        addToBackStack(null).
                        commit();
                break;
        }
    }
}
