package com.ezypayinc.ezypay.controllers.userNavigation.settings.cards;

enum CardDetailViewType {
    VIEWCARD(1), ADDCARD(2), EDITCARD(3);

    private final int mType;

    CardDetailViewType(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }
}
