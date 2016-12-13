package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardDetailView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.CardsPresenters.CardDetailPresenter;
import com.ezypayinc.ezypay.presenter.CardsPresenters.ICardDetailPresenter;

import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.EDITCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.VIEWCARD;

public class CardDetailFragment extends Fragment implements CardsMainActivity.OnBackPressedListener, View.OnClickListener, ICardDetailView {
    private EditText edtCardNumber, edtMonth, edtYear, edtCvv;
    private Button btnSubmit;
    private int mCardDetailViewType, mCardId;
    private View mRootView;
    private Card mCard;
    private ProgressDialog mProgressDiaolog;
    private ICardDetailPresenter presenter;

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
        mRootView = inflater.inflate(R.layout.fragment_card_detail, container, false);
        setupProgressDialog();
        initComponents();
        UserManager userManager = new UserManager();
        if(mCardId != 0){
            mCard = userManager.getCardById(mCardId);
        }
        setupView();
        presenter = new CardDetailPresenter(this);
        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((CardsMainActivity)getActivity()).setListener(this);
    }

    private void setupProgressDialog(){
        mProgressDiaolog = new ProgressDialog(this.getActivity());
        mProgressDiaolog.setCancelable(false);
    }

    public void initComponents() {
        edtCardNumber = (EditText) mRootView.findViewById(R.id.card_detail_card_number);
        edtMonth = (EditText) mRootView.findViewById(R.id.card_detail_month);
        edtYear = (EditText) mRootView.findViewById(R.id.card_detail_year);
        edtCvv = (EditText) mRootView.findViewById(R.id.card_detail_cvv);
        btnSubmit = (Button) mRootView.findViewById(R.id.card_detail_action);
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
        if(mCard != null) {
            edtCardNumber.setText(mCard.getNumber());
            edtMonth.setText(String.valueOf(mCard.getMonth()));
            edtYear.setText(String.valueOf(mCard.getYear()));
            edtCvv.setText(String.valueOf(mCard.getCvv()));
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
        String number = edtCardNumber.getText().toString().replaceAll("\\s+","");
        String cvvString = edtCvv.getText().toString();
        String monthString = edtMonth.getText().toString();
        String yearString = edtYear.getText().toString();
        if (presenter.validateFields(number, cvvString, monthString, yearString)) {
            Card card = new Card();
            card.setNumber(number);
            card.setCvv(Integer.valueOf(cvvString));
            card.setMonth(Integer.valueOf(monthString));
            card.setYear(Integer.valueOf(yearString));
            if (mCardDetailViewType == ADDCARD.getType()) {
                presenter.insertCard(card);
            } else if (mCardDetailViewType == EDITCARD.getType()) {
                card.setId(mCardId);
                presenter.updateCard(card);
            }
        }
    }

    @Override
    public void setError(int component, int errorMessage) {
        EditText editText = (EditText) mRootView.findViewById(component);
        editText.setError(getString(errorMessage));
        editText.requestFocus();
    }

    @Override
    public void onNetworkError(Object error) {
        ErrorHelper.handleError(error, this.getContext());
    }

    @Override
    public void navigateToCardList() {
        super.getActivity().onBackPressed();
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
