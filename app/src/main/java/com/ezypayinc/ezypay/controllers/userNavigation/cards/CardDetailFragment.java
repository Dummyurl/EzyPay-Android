package com.ezypayinc.ezypay.controllers.userNavigation.cards;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;

import static com.ezypayinc.ezypay.controllers.userNavigation.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.cards.CardDetailViewType.EDITCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.cards.CardDetailViewType.VIEWCARD;

public class CardDetailFragment extends Fragment implements CardsMainActivity.OnBackPressedListener, View.OnClickListener {
    private EditText edtCardNumber, edtMonth, edtYear, edtCvv;
    private Button btnSubmit;
    private int mCardDetailViewType, mCardId;
    private Card card;

    public static final String CARD_ID = "CARDID";
    public static final String VIEW_TYPE = "VIEWTYPE";

    public CardDetailFragment() {
        // Required empty public constructor
    }

    public static CardDetailFragment newInstance(int cardId, int cardDetailViewType) {
        CardDetailFragment fragment = new CardDetailFragment();
        Bundle args = new Bundle();
        args.putInt(CARD_ID,cardId);
        args.putInt(VIEW_TYPE, cardDetailViewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCardId = getArguments().getInt(CARD_ID);
            mCardDetailViewType = getArguments().getInt(VIEW_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_card_detail, container, false);
        initComponents(rootView);
        UserManager userManager = new UserManager();
        if(mCardId != 0){
            card = userManager.getCardById(mCardId);
        }
        setupView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((CardsMainActivity)getActivity()).setListener(this);
    }

    public void initComponents(View rootView) {
        edtCardNumber = (EditText) rootView.findViewById(R.id.card_detail_card_number);
        edtMonth = (EditText) rootView.findViewById(R.id.card_detail_month);
        edtYear = (EditText) rootView.findViewById(R.id.card_detail_year);
        edtCvv = (EditText) rootView.findViewById(R.id.card_detail_cvv);
        btnSubmit = (Button) rootView.findViewById(R.id.card_detail_action);
        btnSubmit.setOnClickListener(this);
    }

    public void setupView() {
        if(mCardDetailViewType == VIEWCARD.getType()) {
            setupViewCardView();
            setComponentsWithCard();
        } else if(mCardDetailViewType == ADDCARD.getType()) {
            setupAddCardView();
        } else {
            setupEditCardView();
            setComponentsWithCard();
        }
    }

    public void setupViewCardView() {
        setHasOptionsMenu(true);
        setupComponents(false);
    }

    public void setupAddCardView() {
        setHasOptionsMenu(false);
        btnSubmit.setText(R.string.action_card_detail_add_card);
        setupComponents(true);
    }

    public void setupEditCardView() {
        setHasOptionsMenu(false);
        btnSubmit.setText(R.string.action_card_detail_edit_card);
        setupComponents(true);
    }

    public void setupComponents(boolean enable) {
        edtCardNumber.setEnabled(enable);
        edtMonth.setEnabled(enable);
        edtYear.setEnabled(enable);
        edtCvv.setEnabled(enable);
        btnSubmit.setVisibility(enable? View.VISIBLE:View.GONE);

    }

    public void setComponentsWithCard() {
        if(card != null) {
            edtCardNumber.setText(card.getNumber());
            edtMonth.setText(String.valueOf(card.getMonth()));
            edtYear.setText(String.valueOf(card.getYear()));
            edtCvv.setText(String.valueOf(card.getCvv()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.card_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.edit_card_item) {
            mCardDetailViewType = EDITCARD.getType();
            setupEditCardView();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        if(mCardDetailViewType == VIEWCARD.getType()) {
            return true;
        } else if(mCardDetailViewType == ADDCARD.getType()) {
            return true;
        } else {
            mCardDetailViewType = VIEWCARD.getType();
            setupViewCardView();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }
}
