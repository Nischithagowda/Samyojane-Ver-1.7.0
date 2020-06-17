package com.bhoomi.Samyojane_Application;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AlertDialogClass_Alert {
    private Context context;
    private String btnText;
    private String type;
    private  String msg;

    public AlertDialogClass_Alert(Context context, String btnText, String type, String msg) {
        this.context = context;
        this.btnText = btnText;
        this.type = type;
        this.msg = msg;
    }

    public  void displayDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle(this.type);

        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage(this.msg);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(this.btnText, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
