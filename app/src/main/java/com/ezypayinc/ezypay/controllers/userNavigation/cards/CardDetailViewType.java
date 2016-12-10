package com.ezypayinc.ezypay.controllers.userNavigation.cards;

/**
 * Created by Gustavo Quesada S on 09/12/2016.
 */

public enum CardDetailViewType {
    VIEWCARD(1), ADDCARD(2), EDITCARD(3);

    private final int mType;

    CardDetailViewType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
