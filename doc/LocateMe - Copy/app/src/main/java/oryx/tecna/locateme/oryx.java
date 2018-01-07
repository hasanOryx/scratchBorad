package oryx.tecna.locateme;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Hasan Yousef on 25/04/2017.
 */

public class oryx extends Application {
    private static Context mContext;
    private static ContentResolver mResolver;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mResolver = getContentResolver();
    }

    public static Context getContext() {
        return mContext;
    }
    public static ContentResolver getResolver() {
        return mResolver;
    }

    public static AlertDialog alertDialog(Context cntx) {
        Toast.makeText(cntx, "toast", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                cntx);

        // set title
        alertDialogBuilder.setTitle("Invalid number");

        // set dialog message
        alertDialogBuilder
                .setMessage("The selected number should start by '+' with international code, "+
                        "please go to your contacts and fix the number format:\n"+
                        "Example in Mexico, the number should start by: +52")
                .setCancelable(false)
                .setPositiveButton("CLOSE APP",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity

                       // finish();
                    }
                })
                .setNegativeButton("Pick another number",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                })
                .setNeutralButton("EDIT number",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialog) {

                Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setFocusable(true);
                negative.setFocusableInTouchMode(true);
                negative.requestFocus();
            }
        });

        return alertDialog;
        // show it
  //      alertDialog.show();

    }

}