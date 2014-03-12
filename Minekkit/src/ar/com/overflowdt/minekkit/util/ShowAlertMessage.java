package ar.com.overflowdt.minekkit.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by Fede on 12/03/14.
 */
public class ShowAlertMessage {
    public static void showMessage(String message,Activity act) {
        final AlertDialog.Builder notlogged = new AlertDialog.Builder(act);
        notlogged.setTitle("Aviso");
        notlogged.setMessage(message);
        notlogged.setCancelable(false);
        notlogged.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {}
        });
        act.runOnUiThread(new Runnable() {

            public void run() {
                notlogged.show();
            }
        });

    }
}
