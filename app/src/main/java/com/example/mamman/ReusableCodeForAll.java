package com.example.mamman;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ReusableCodeForAll {
    public static boolean comfirm=false;

    public static void ShowAlert(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                comfirm=true;
                dialog.dismiss();
            }
        }).setTitle(title).setMessage(message).show();
    }

}
