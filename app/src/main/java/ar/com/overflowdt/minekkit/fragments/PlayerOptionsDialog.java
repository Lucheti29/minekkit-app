package ar.com.overflowdt.minekkit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import ar.com.overflowdt.minekkit.R;
import ar.com.overflowdt.minekkit.activities.NewPMActivity;

/**
 * Created by Fede on 26/12/2014.
 */
public class PlayerOptionsDialog extends DialogFragment {

    //estos valores deben corresponderse con el orden definido en el
    //array picture_upload_options.xml
    private static final int OPTION_SENDPM = 0;
    private String playerName;

    //el elemento que va a recibir la imagen obtenido
    //private View target;

    public void setPlayerName(String player) {
        playerName = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(playerName)
                .setItems(R.array.players_options_array, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position of the selected item
                        switch (which) {
                            case OPTION_SENDPM:
                                Intent i = new Intent(getActivity(), NewPMActivity.class);
                                i.putExtra(NewPMActivity.TAG_TO, playerName);
                                getActivity().startActivity(i);
                                break;
                            default:
                                dismiss();
                                break;
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}