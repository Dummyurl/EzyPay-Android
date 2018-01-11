package com.ezypayinc.ezypay.presenter.LoginPresenters.commerce;

import android.graphics.Bitmap;

import com.ezypayinc.ezypay.base.UserSingleton;
import com.ezypayinc.ezypay.controllers.login.commerce.interfaceViews.ISignInPhotoCommerceView;
import com.ezypayinc.ezypay.model.User;


public class SignInPhotoCommercePresenter implements ISignInPhotoCommercePresenter {

    private ISignInPhotoCommerceView mView;

    public SignInPhotoCommercePresenter(ISignInPhotoCommerceView view) {
        mView = view;
    }


    @Override
    public void saveCommerceData(Bitmap profilePhoto, int tablesQuantity) {
        User commerce = UserSingleton.getInstance().getUser();
        if (commerce != null) {
            if(profilePhoto != null) {
                UserSingleton.getInstance().setUserProfilePhoto(profilePhoto);
                int userType = tablesQuantity > 0 ? 2 : 3;
                commerce.setUserType(userType);
                UserSingleton.getInstance().setTablesQuantity(tablesQuantity);
                UserSingleton.getInstance().setUser(commerce);
                mView.goToNextView();
            } else {
                mView.showImageRequiredError();
            }
        }
    }
}
