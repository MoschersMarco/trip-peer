package com.example.schake.trip_peer.utils;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.schake.trip_peer.Data.Trip;
import com.example.schake.trip_peer.Data.TripManager;
import com.example.schake.trip_peer.R;

public class MarkCurrentTripAsArchivedDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final TripManager manager = TripManager.getInstance();

        String currentTripName = manager.getCurrentTrip().getName();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Wollen Sie den aktuellen Urlaub " + currentTripName + " wirklich beenden und einen neuen starten?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        manager.archivCurrentTrip();
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}