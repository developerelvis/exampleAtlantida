package com.example.basicexample.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;
import dmax.dialog.SpotsDialog;

public class Commons {
    public static void alertDialog(Context context, String title, String content, String textInButton) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        alertDialog.setNegativeButton(textInButton, (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public static void alertDialog(Context context, String title, String content, String negativeButton, String positiveButton,
                                   DialogInterface.OnClickListener listenerNegative, DialogInterface.OnClickListener listenerPositive) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(negativeButton,  listenerNegative);
        alertDialog.setPositiveButton(positiveButton, listenerPositive);
        alertDialog.show();
    }

    public static void showSnackbar(View view, String content) {
        Snackbar.make(view, content, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public static AlertDialog showLoading(Context context, String message, boolean cancelable) {
        return new SpotsDialog.Builder()
                .setContext(context)
                .setMessage(message)
                .setCancelable(cancelable)
                .build();
    }

    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;
        if (email.contains("@")) return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        else return false;
    }

    public static boolean validationLettersSpace(String text)
    {
        try
        {
            if(TextUtils.isEmpty(text.trim())){
                return true;
            }
            Pattern patron = Pattern.compile("^[a-zA-Z-' ]+$");
            if (patron.matcher(text).matches())
            {
                return true;
            }
        } catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean validationLettersNumbers(String text)
    {
        try
        {
            if(TextUtils.isEmpty(text.trim())){
                return true;
            }
            Pattern patron = Pattern.compile("^[a-zA-Z0-9]+$");
            if (patron.matcher(text).matches())
            {
                return true;
            }
        } catch (OutOfMemoryError exception)
        {
            exception.printStackTrace();
        }
        return false;
    }
}
