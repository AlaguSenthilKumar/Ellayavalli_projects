package com.alwar.elayavalli.Others;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class AppCommUtils {

    public static void showAlertDialog(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage(message);

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
