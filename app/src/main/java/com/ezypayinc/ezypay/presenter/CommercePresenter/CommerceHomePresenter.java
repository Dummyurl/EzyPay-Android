package com.ezypayinc.ezypay.presenter.CommercePresenter;


import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.commerceNavigation.commerce.interfaceViews.ICommerceHomeView;
import com.ezypayinc.ezypay.model.Payment;
import com.ezypayinc.ezypay.model.User;

public class CommerceHomePresenter implements ICommerceHomePresenter {

    private ICommerceHomeView mView;

    public CommerceHomePresenter(ICommerceHomeView view) {
        mView = view;
    }

    @Override
    public void setupView() {
        mView.showProgressDialog();
        User user = UserSingleton.getInstance().getUser();
        if(user.getUserType() == 4) {
            String name = String.format("%s %s", user.getName(), user.getLastName());
            mView.displayUserInformation(user.getEmployeeBoss().getAvatar(), name, user.getEmployeeBoss().getName());
        } else {
            mView.displayUserInformation(user.getAvatar(), user.getName(), user.getName());
        }
        mView.hideProgressDialog();
    }

    @Override
    public void generateQrCode() {
        User currentUser = UserSingleton.getInstance().getUser();
        if(currentUser.getUserType() == 4 && currentUser.getEmployeeBoss().getUserType() == 2) {
            mView.displayCommerceTableListView();
        } else {
            Payment payment = new Payment();
            User user = UserSingleton.getInstance().getUser();
            if(user.getUserType() == 4) {
                payment.setEmployeeId(user.getId());
            } else {
                payment.setEmployeeId(0);
            }
            mView.generateQRCode(payment);
        }
    }
}
