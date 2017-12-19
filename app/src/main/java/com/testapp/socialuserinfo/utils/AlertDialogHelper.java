package com.testapp.socialuserinfo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;

import io.reactivex.functions.Action;

public class AlertDialogHelper {

    public static void showDialog(Context context, String title, String description) {
        showDialog(context, title, description, null, null, null);
    }

    public static void showDialog(Context context, String title, String description, String negativeButtonText, String positiveButtonText, Action onPositiveClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, /*R.style.AlertDialogStyle*/ 0)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(description);

        builder.setPositiveButton(positiveButtonText == null ? "OK" : positiveButtonText, (dialog1, which) -> {
            if (onPositiveClick != null) {
                try {
                    onPositiveClick.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if (!TextUtils.isEmpty(negativeButtonText))
            builder.setNegativeButton(negativeButtonText, null);

        AlertDialog alert = builder.create();
        if (alert != null) {
            alert.show();

        }


    }

}
