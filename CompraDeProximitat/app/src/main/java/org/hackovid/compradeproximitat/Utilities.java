package org.hackovid.compradeproximitat;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;

import androidx.appcompat.app.AlertDialog;

import java.util.List;
import java.util.Locale;

public class Utilities
{
    public static String MD5(String md5)
    {
        try
        {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; ++i)
            {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
        }
        return null;
    }


    public static void alertDialogBuilder(Context context, String title, String message)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Title");
        dialog.setMessage("Write your message here.");
        dialog.setCancelable(true);

// Specifying a listener allows you to take an action before dismissing the dialog.
// The dialog is automatically dismissed when a dialog button is clicked.

        dialog.setPositiveButton(
                "Yes",
                (dialog1, id) ->
                {
                    // Continue with some operation
                    dialog1.cancel();
                });

// A null listener allows the button to dismiss the dialog and take no further action.

     /*   dialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert = dialog.create();
        alert.show();
    }
}
