package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ezypayinc.ezypay.R;
import com.ezypayinc.ezypay.connection.ErrorHelper;
import com.ezypayinc.ezypay.controllers.Helpers.CardValidatorTextWatcher;
import com.ezypayinc.ezypay.controllers.Helpers.CreditCardValidator;
import com.ezypayinc.ezypay.controllers.Helpers.ExpDateValidatorTextWatcher;
import com.ezypayinc.ezypay.controllers.Helpers.RightDrawableOnTouchListener;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.SettingsMainActivity;
import com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.interfaceViews.ICardDetailView;
import com.ezypayinc.ezypay.manager.UserManager;
import com.ezypayinc.ezypay.model.Card;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters.CardDetailPresenter;
import com.ezypayinc.ezypay.presenter.SettingsPresenters.CardsPresenters.ICardDetailPresenter;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.realm.Realm;

import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.ADDCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.EDITCARD;
import static com.ezypayinc.ezypay.controllers.userNavigation.settings.cards.CardDetailViewType.VIEWCARD;

public class CardDetailFragment extends Fragment implements CardsMainActivity.OnBackPressedListener, View.OnClickListener, ICardDetailView {
    private EditText edtCardNumber, edtExpDate, edtCvv;
    private Button btnSubmit;
    private int mCardDetailViewType, mCardId;
    private View mRootView;
    private Card mCard;
    private ProgressDialog mProgressDialog;
    private ICardDetailPresenter presenter;

    public static final int SCAN_REQUEST_CODE = 200;
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
        mProgressDialog = new ProgressDialog(this.getActivity());
        mProgressDialog.setCancelable(false);
    }

    public void initComponents() {
        edtCardNumber = (EditText) mRootView.findViewById(R.id.card_detail_card_number);
        edtExpDate = (EditText) mRootView.findViewById(R.id.card_detail_exp_date);
        edtCvv = (EditText) mRootView.findViewById(R.id.card_detail_cvv);
        btnSubmit = (Button) mRootView.findViewById(R.id.card_detail_action);
        btnSubmit.setOnClickListener(this);
        edtExpDate.addTextChangedListener(new ExpDateValidatorTextWatcher(edtExpDate));
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
        edtCardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setHasOptionsMenu(true);
        setupComponents(false);
    }

    public void setupAddCardView() {
        setHasOptionsMenu(false);
        btnSubmit.setText(R.string.action_card_detail_add_card);
        setupComponents(true);
        edtCardNumber.addTextChangedListener(new CardValidatorTextWatcher());
        edtCardNumber.setOnTouchListener(new RightDrawableOnTouchListener(edtCardNumber) {
            @Override
            public boolean onDrawableTouch(MotionEvent event) {
                onScanPress();
                return true;
            }
        });
    }

    public void setupEditCardView() {
        edtCardNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setHasOptionsMenu(false);
        btnSubmit.setText(R.string.action_card_detail_edit_card);
        setupComponents(true);
        edtCardNumber.setEnabled(false);
    }

    public void setupComponents(boolean enable) {
        edtCardNumber.setEnabled(enable);
        edtExpDate.setEnabled(enable);
        edtCvv.setEnabled(enable);
        btnSubmit.setVisibility(enable? View.VISIBLE:View.GONE);

    }

    public void setComponentsWithCard() {
        if(mCard != null) {
            String month = mCard.getExpirationDate().substring(0,3);
            String year = mCard.getExpirationDate().substring(5,7);
            String expDate = month + year;
            edtCardNumber.setText(mCard.getCardNumber());
            edtExpDate.setText(expDate);
            edtCvv.setText(String.valueOf(mCard.getCcv()));
        }
    }

    private void onScanPress() {
        Intent scanIntent = new Intent(getActivity(), CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);
        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                edtCardNumber.setText(scanResult.cardNumber);
                int year = scanResult.expiryYear % 2000;
                edtExpDate.setText(scanResult.expiryMonth + "/" + year);
                edtCvv.setText(scanResult.cvv);
            }
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
        String ccvString = edtCvv.getText().toString();
        String expDate = edtExpDate.getText().toString();
        expDate = expDate.substring(0,3) + "20" + expDate.substring(3,5);

        if(mCardDetailViewType == ADDCARD.getType()) {
            insertCard(number,ccvString,expDate);
        } else {
            updateCard(ccvString, expDate);
        }
    }

    public void insertCard(String number, String ccvString, String expDate) {
        CreditCard creditCard = new CreditCard();
        creditCard.cardNumber = number;
        if (presenter.validateFields(number, ccvString, expDate, creditCard.getCardType())) {
            CreditCardValidator validator = new CreditCardValidator();
            Card card = new Card();
            card.setCardNumber(number);
            card.setCcv(Integer.parseInt(ccvString));
            card.setExpirationDate(expDate);
            card.setCardVendor(validator.getCardType(creditCard.getCardType()));
            presenter.insertCard(card);
        }
    }

    public void updateCard(String cvv, String expDate) {
        if(presenter.validateFields(cvv, expDate)) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mCard.setCcv(Integer.parseInt(cvv));
            mCard.setExpirationDate(expDate);
            realm.commitTransaction();
            presenter.updateCard(mCard);
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
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.custom_progress_dialog);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }
}
