package com.ezypayinc.ezypay.controllers.Dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogBuilder {

    private Context mContext;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String mTitle, mMessage;
    private boolean mCancelable;

    public DialogBuilder(Context context) {
        mContext = context;
    }

    public DialogBuilder(Context context, String message, boolean cancelable) {
        mContext = context;
        mMessage = message;
    }

    public DialogBuilder(Context context, String title, String message, boolean cancelable) {
        mContext = context;
        mTitle = title;
        mMessage = message;
        mCancelable = cancelable;
    }

    public void defaultAlertDialog(String message){
        mMessage = message;
        builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void buildAlertDialog() {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mTitle);
        builder.setMessage(mMessage);
        builder.setCancelable(mCancelable);
    }

    public void setPositiveButton(int buttonText, DialogInterface.OnClickListener listener) {
        builder.setPositiveButton(buttonText, listener);
    }

    public void setNegativeButton(int buttonText, DialogInterface.OnClickListener listener) {
        builder.setNegativeButton(buttonText, listener);
    }

    public void showAlertDialog() {
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissAlertDialog() {
        alertDialog.dismiss();
    }
}
