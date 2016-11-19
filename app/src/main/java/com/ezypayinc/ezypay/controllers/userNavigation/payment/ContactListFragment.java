package com.ezypayinc.ezypay.controllers.userNavigation.payment;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
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
import com.ezypayinc.ezypay.controllers.userNavigation.payment.Adapters.ContactListAdapter;
import com.ezypayinc.ezypay.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListFragment extends Fragment {
    private RecyclerView contactsRecyclerView;
    private ContactListAdapter mAdapter;
    private List<Contact> contactsList;

    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance() {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        contactsRecyclerView = (RecyclerView) rootView.findViewById(R.id.payment_contact_recycler_view);
        contactsList = getContacts();
        mAdapter = new ContactListAdapter(contactsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        contactsRecyclerView.setLayoutManager(mLayoutManager);
        contactsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactsRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public List<Contact> getContacts() {
        String projection[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        String sortByName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,null,null, sortByName);
        List<Contact> contactsList = new ArrayList<>();
        while (phones.moveToNext())
        {
            String name =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(name, phoneNumber);
            if(!contactsList.contains(contact)) {
                contactsList.add(contact);
            }
        }
        phones.close();
        return contactsList;
    }

    public void filterList(String query) {
        ArrayList<Contact> filterList = new ArrayList<>();
        for (Contact contact: contactsList) {
            if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                filterList.add(contact);
            }
        }

        mAdapter.setFilter(filterList);
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
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
