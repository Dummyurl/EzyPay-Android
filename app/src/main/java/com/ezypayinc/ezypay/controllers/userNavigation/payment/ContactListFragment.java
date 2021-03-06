package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.ContactListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.IContactsListView;
import com.ezypayinc.ezypay.model.Friend;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.ContactsListPresenter;
import com.ezypayinc.ezypay.presenter.PaymentPresenters.IContactsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment implements IContactsListView, ContactListAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView contactsRecyclerView;
    private ContactListAdapter mAdapter;
    private FloatingActionButton fabNextAction;
    private List<Friend> friendsList;
    private ArrayList<Friend> friendsSelected;
    private IContactsListPresenter presenter;
    private Payment mPayment;

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance(Payment payment) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putParcelable(PaymentMainActivity.PAYMENT_KEY, payment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            mPayment = getArguments().getParcelable(PaymentMainActivity.PAYMENT_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        contactsRecyclerView = (RecyclerView) rootView.findViewById(R.id.payment_contact_recycler_view);
        presenter = new ContactsListPresenter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        contactsRecyclerView.setLayoutManager(mLayoutManager);
        contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        presenter.getContacts();
        friendsSelected = new ArrayList<>();
        fabNextAction = (FloatingActionButton)rootView.findViewById(R.id.payment_contact_next_action_fab);
        fabNextAction.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.contacts_view_title);
    }

    @Override
    public void setFilter(List<Friend> contacts) {
        mAdapter.setFilter(contacts);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_bar_menu, menu);
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_option).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.filterContacts(query, friendsList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.filterContacts(newText, friendsList);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayListOfContacts(List<Friend> friends) {
        friendsList = friends;
        mAdapter = new ContactListAdapter(friendsList, this, getContext());
        contactsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, getContext());
    }

    @Override
    public void OnItemClickListener(Friend friend, boolean isChecked) {
        if(isChecked) {
            friendsSelected.add(friend);
        } else {
            friendsSelected.remove(friend);
        }

        if (friendsSelected.isEmpty()) {
            fabNextAction.setVisibility(View.GONE);
        } else {
            fabNextAction.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payment_contact_next_action_fab:
                presenter.removeFriendsFromPayment(mPayment);
                mPayment.getFriends().addAll(friendsSelected);
                Fragment fragment = SplitFragment.newInstance(mPayment);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.payment_main_container, fragment).
                        addToBackStack(null).
                        commit();
                break;
            default:
                break;
        }
    }
}
