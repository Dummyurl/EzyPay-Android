package com.ezypayinc.ezypay.controllers.userNavigation.cards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.cards.adapters.CardsListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.CardsPresenters.CardListPresenter;
import com.ezypayinc.ezypay.presenter.CardsPresenters.ICardListPresenter;

import java.util.List;

import static com.ezypayinc.ezypay.controllers.userNavigation.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.cards.CardDetailViewType.VIEWCARD;

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
        presenter.getCardsByUser();
        setHasOptionsMenu(true);
        return rootView;
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
            Intent intent = new Intent(getActivity(), CardsMainActivity.class);
            Bundle args = new Bundle();
            args.putInt(CardDetailFragment.CARD_ID, 0);
            args.putInt(CardDetailFragment.VIEW_TYPE, ADDCARD.getType());
            intent.putExtras(args);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(Card card) {
        Intent intent = new Intent(getActivity(), CardsMainActivity.class);
        Bundle args = new Bundle();
        args.putInt(CardDetailFragment.CARD_ID, card.getId());
        args.putInt(CardDetailFragment.VIEW_TYPE, VIEWCARD.getType());
        intent.putExtras(args);
        startActivity(intent);
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
