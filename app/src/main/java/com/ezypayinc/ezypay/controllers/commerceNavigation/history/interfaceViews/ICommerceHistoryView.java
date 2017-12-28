package com.ezypayinc.ezypay.controllers.commerceNavigation.history.interfaceViews;

import com.ezypayinc.ezypay.model.CommerceHistory;
import com.ezypayinc.ezypay.model.HistoryDate;

import java.util.List;

public interface ICommerceHistoryView {

    void showHistoryRecords(List<CommerceHistory> userHistory, List<HistoryDate> datesList);
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
}
