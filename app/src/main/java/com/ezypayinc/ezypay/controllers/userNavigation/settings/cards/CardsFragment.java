package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.adapters.CardsListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.CardsPresenters.CardListPresenter;
import com.ezypayinc.ezypay.presenter.CardsPresenters.ICardListPresenter;

import java.util.List;

import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.VIEWCARD;

public class CardsFragment extends Fragment implements CardsListAdapter.OnItemClickListener, ICardListView {
    private RecyclerView cardListRecyclerView;
    private CardsListAdapter adapter;
    private ICardListPresenter presenter;
    private ProgressDialog mProgressDiaolog;

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
        setupProgressDialog();
        presenter = new CardListPresenter(this);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCardsByUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.cards_view_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_card_item) {
            Fragment fragment = CardDetailFragment.newInstance(0, ADDCARD.getType());
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().
                    replace(R.id.cards_main_container, fragment).
                    addToBackStack(null).
                    commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(Card card) {
        Fragment fragment = CardDetailFragment.newInstance(card.getId(), VIEWCARD.getType());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.cards_main_container, fragment).
                addToBackStack(null).
                commit();
    }

    private void setupProgressDialog(){
        mProgressDiaolog = new ProgressDialog(this.getActivity());
        mProgressDiaolog.setCancelable(false);
    }


    @Override
    public void populateCardList(List<Card> cards) {
        adapter = new CardsListAdapter(getContext(), this, cards);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        cardListRecyclerView.setLayoutManager(mLayoutManager);
        cardListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cardListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, this.getContext());
    }

    @Override
    public void showProgressDialog() {
        mProgressDiaolog.show();
        mProgressDiaolog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDiaolog.hide();
    }
}
