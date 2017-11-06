package com.ezypayinc.ezypay.connection;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.ezypayinc.ezypay.controllers.Dialogs.DialogBuilder;


public class ErrorHelper {


    public static void handleError(Object error, Context context) {
        if(error instanceof AuthFailureError) {
            handleAuthFailureError(context);
        } else if(error instanceof NetworkError) {
            handleNetworkError(context);
        } else {
            handleComunicationError(context);
        }
    }


    private static void handleAuthFailureError(Context context) {
        DialogBuilder dialogBuilder = new DialogBuilder(context);
        dialogBuilder.defaultAlertDialog("Your session is expired");
    }

    private static void handleNetworkError(Context context) {
        DialogBuilder dialogBuilder = new DialogBuilder(context);
        dialogBuilder.defaultAlertDialog("Please verify your internet connection");
    }

    private static void handleComunicationError(Context context) {
        DialogBuilder dialogBuilder = new DialogBuilder(context);
        dialogBuilder.defaultAlertDialog("Communication Error");
    }
}
