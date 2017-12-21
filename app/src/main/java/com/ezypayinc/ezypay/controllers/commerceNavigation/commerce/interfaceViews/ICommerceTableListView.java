package com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews;

import com.ezypayinc.ezypay.model.Table;
import java.util.List;


public interface ICommerceTableListView {

    void showProgressDialog();
    void dismissProgressDialog();
    void showTables(List<Table> tables);
    void onNetworkError(Object error);
}
