package com.ezypayinc.ezypay.controllers.userNavigation.history.interfaceViews;

import com.ezypayinc.ezypay.model.HistoryDate;
import com.ezypayinc.ezypay.model.UserHistory;
import java.util.List;

public interface IHistoryView {

    void showHistoryRecords(List<UserHistory> userHistory, List<HistoryDate> datesList);
    void showProgressDialog();
    void dismissProgressDialog();
    void onNetworkError(Object error);
}
