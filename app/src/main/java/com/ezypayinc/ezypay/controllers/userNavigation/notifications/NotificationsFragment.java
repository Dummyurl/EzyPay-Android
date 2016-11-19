package com.ezypayinc.ezypay.controllers.userNavigation.notifications;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.controllers.userNavigation.notifications.adapters.HistoryListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.notifications.adapters.NotificationListAdapter;

public class NotificationsFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.notification_fragment_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new NotificationListAdapter());
        setupTap();
        return rootView;
    }

    private void setupTap() {
        TabLayout.Tab tabNotification = tabLayout.newTab();
        tabNotification.setText(getString(R.string.tab_notification_fragment_notification));
        TabLayout.Tab tabHistory = tabLayout.newTab();
        tabHistory.setText(getString(R.string.tab_notification_fragment_history));

        tabLayout.addTab(tabNotification);
        tabLayout.addTab(tabHistory);

        tabLayout.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        recyclerView.setAdapter(null);
        RecyclerView.Adapter adapter = null;
        if(tab.getPosition() == 0){
            adapter = new NotificationListAdapter();
        } else {
            adapter = new HistoryListAdapter();
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
