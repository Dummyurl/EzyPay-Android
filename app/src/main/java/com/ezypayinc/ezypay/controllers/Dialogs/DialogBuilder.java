package com.ezypayinc.ezypay.controllers.Dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogBuilder {

    private Context mContext;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private String mMessage;

    public DialogBuilder(Context context) {
        mContext = context;
    }

    public void showAlertDialog(String message){
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
}
