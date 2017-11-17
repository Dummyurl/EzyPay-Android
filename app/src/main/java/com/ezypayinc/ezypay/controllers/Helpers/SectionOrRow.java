package com.ezypayinc.ezypay.controllers.Helpers;


public class SectionOrRow {

    private String mHeader;
    private Object mRow;
    private boolean mIsRow;

    public static SectionOrRow createRow(Object row) {
        SectionOrRow ret = new SectionOrRow();
        ret.mRow = row;
        ret.mIsRow = true;
        return ret;
    }

    public static SectionOrRow createHeader(String header) {
        SectionOrRow ret = new SectionOrRow();
        ret.mHeader = header;
        ret.mIsRow = false;
        return ret;
    }

    public String getHeader() {
        return mHeader;
    }


    public Object getRow() {
        return mRow;
    }

    public boolean isRow() {
        return mIsRow;
    }

}
