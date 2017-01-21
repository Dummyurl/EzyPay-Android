package com.ezypayinc.ezypay.presenter.PaymentPresenters;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezypayinc.ezypay.controllers.userNavigation.payment.interfaceViews.ContactsListView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Contact;
import com.ezypayinc.ezypay.model.User;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavoquesada on 1/14/17.
 */

public class ContactsListPresenter implements IContactsListPresenter {
    private ContactsListView mView;

    public ContactsListPresenter(ContactsListView view) {
        mView = view;
    }

    @Override
    public void getContacts() {
        String projection[] = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        JSONArray array = new JSONArray();
        String sortByName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor phones = ((Fragment) mView).getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,null,null, sortByName);
        List<Contact> contactsList = new ArrayList<>();
        while (phones.moveToNext())
        {
            String name =phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(name, phoneNumber);
            if(!contactsList.contains(contact)) {
                array.put(phoneNumber);
                contactsList.add(contact);
            }
        }
        phones.close();
        validatePhoneNumbers(array);
    }

    @Override
    public void filterContacts(String query, List<User> usersList) {
        ArrayList<User> filterList = new ArrayList<>();
        for (User user: usersList) {
            String fullname = user.getName() + " " + user.getLastName();
            if (fullname.toLowerCase().contains(query.toLowerCase())) {
                filterList.add(user);
            }
        }

        mView.setFilter(filterList);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public void validatePhoneNumbers(JSONArray array) {
        final UserManager userManager = new UserManager();

        try {
            userManager.validatePhoneNumbers(array, new Response.Listener<JsonElement>() {
                @Override
                public void onResponse(JsonElement response) {
                   if(response.isJsonArray()) {
                       List<User> userList = userManager.parseValidatePhoneNumbers(response);
                       mView.displayListOfContacts(userList);
                   }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mView.onNetworkError(error);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
