package com.vvc.addpartoapp.presentation.globalutils.singleton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.vvc.addpartoapp.R;
import com.vvc.addpartoapp.presentation.globalutils.constants.GlobalMethods;


/**
 * Created by durgaprasad.polaki on 16/06/18.
 */

public class SingletonClass {
    private static SingletonClass INSTANCE = null;

    private SingletonClass() {
    }

    public static SingletonClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonClass();
        }
        return INSTANCE;
    }


    public boolean isDeviceOnline(Context context) throws Exception {
        try {
            final ConnectivityManager conMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                // notify user you are online
                return true;
            } else {
                // notify user you are not online
                return false;
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void showDefaultDialogWithFinish(final Activity context, String title, String messag) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage(messag)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        context.finish();
                    }
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(title);
        alert.show();
    }


    public void showDefaultDialog(Activity context, String title, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_alert);
        dialog.setCanceledOnTouchOutside(false);
        // set the custom dialog components - text, image and button

        (dialog.findViewById(R.id.two_btns_layout)).setVisibility(View.GONE);
        (dialog.findViewById(R.id.ok_layout)).setVisibility(View.VISIBLE);
        TextView tv_headerText = (TextView) dialog
                .findViewById(R.id.tv_headerText);

        tv_headerText.setText(title);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(message);
        text.setTextColor(Color.BLACK);

        Button dialogButton = (Button) dialog
                .findViewById(R.id.dialogButtonOK);

        Button dialogCancelbtn = (Button) dialog
                .findViewById(R.id.dialogCancelbtn);
        dialogCancelbtn.setVisibility(View.GONE);

        Button ok_default_btn = (Button) dialog.findViewById(R.id.ok_default_btn);
        ok_default_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Button dialogCancelbtn = (Button) dialog
        // .findViewById(R.id.dialogCancelbtn);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialogCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }





    public void showAlertDialogWithTwoButtons(final Activity context, String title,
                                              String message) throws Exception {
        try {

            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert);
            dialog.setCanceledOnTouchOutside(false);
            // set the custom dialog components - text, image and button

            TextView tv_headerText = (TextView) dialog
                    .findViewById(R.id.tv_headerText);

            tv_headerText.setText(title);
            TextView text = (TextView) dialog.findViewById(R.id.text);
            text.setText(message);
            text.setTextColor(Color.BLACK);

            Button dialogButton = (Button) dialog
                    .findViewById(R.id.dialogButtonOK);

            Button dialogCancelbtn = (Button) dialog
                    .findViewById(R.id.dialogCancelbtn);


            // Button dialogCancelbtn = (Button) dialog
            // .findViewById(R.id.dialogCancelbtn);

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialogCancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        } catch (Exception e) {
            throw e;
        }
    }

    public static AlertDialog.Builder getAlertdialog(Context context, String title, String message, String positiveButtonMessage, DialogInterface.OnClickListener positiveButtonOnClickListener,
                                                     String negativeButtonMessage, DialogInterface.OnClickListener negativeButtonOnClickListener, boolean isCancelable) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        if (GlobalMethods.isNull(title)) {
            dlgAlert.setTitle(title);
        }
        if (positiveButtonMessage != null && !positiveButtonMessage.isEmpty()) {
            dlgAlert.setPositiveButton(positiveButtonMessage, positiveButtonOnClickListener);
        }
        if (negativeButtonMessage != null && !negativeButtonMessage.isEmpty()) {
            dlgAlert.setNegativeButton(negativeButtonMessage, negativeButtonOnClickListener);
        }
        dlgAlert.setCancelable(isCancelable);
        return dlgAlert;
    }

    public static AlertDialog.Builder getAlertdialog(Context context, String s, String string, DialogInterface.OnClickListener clickListener, String message, DialogInterface.OnClickListener onClickListener, boolean b) {
        return getAlertdialog(context, "", message, context.getResources().getString(android.R.string.ok), null, "", null, true);
    }

    public static AlertDialog.Builder getAlertdialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = getAlertdialog(context, "", message, context.getResources().getString(android.R.string.ok), null, "", null, true);
        alertDialog.setTitle(title);
        return alertDialog;
    }
}
