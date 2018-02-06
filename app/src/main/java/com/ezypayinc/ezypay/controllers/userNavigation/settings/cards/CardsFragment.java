package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;
import com.ezypayinc.ezypay.controllers.Helpers.SectionOrRow;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.adapters.CardsListAdapter;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardListView;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters.CardListPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters.ICardListPresenter;

import java.util.List;

import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.VIEWCARD;

public class CardsFragment extends Fragment implements CardsListAdapter.OnItemClickListener, ICardListView {
    private RecyclerView cardListRecyclerView;
    private ICardListPresenter presenter;
    private ProgressDialog mProgressDialog;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cards, container, false);
        cardListRecyclerView = rootView.findViewById(R.id.cards_list_recycler_view);
        setupProgressDialog();
        presenter = new CardListPresenter(this);
        setHasOptionsMenu(true);
        setTitle();
        return rootView;
    }

    private void setTitle() {
        getActivity().setTitle(R.string.title_cards_list_fragment);
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

    @Override
    public void onLongClickListener(final Card card) {
        String title = getString(R.string.delete_card_title);
        String message = getString(R.string.delete_card_confirmation_message);
        final DialogBuilder builder = new DialogBuilder(getContext(),title, message, false);
        builder.buildAlertDialog();
        builder.setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(card.isFavorite() != 1) {
                    presenter.deleteCard(card);
                } else {
                    builder.dismissAlertDialog();
                    showErrorDeleteFavoriteCard();
                }
            }
        });
        builder.setNegativeButton(R.string.action_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.showAlertDialog();
    }

    private void showErrorDeleteFavoriteCard() {
        String message = getString(R.string.error_delete_favorite_card);
        DialogBuilder builder = new DialogBuilder(getContext());
        builder.defaultAlertDialog(message);
    }

    private void setupProgressDialog(){
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }


    @Override
    public void populateCardList(List<Card> cards) {
        CardsListAdapter adapter = new CardsListAdapter(getContext(), this, cards);
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
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }
}
