package com.example.schake.trip_peer.utils;

import android.content.Intent;
import android.view.View;

import com.example.schake.trip_peer.UrlaubArchiviert;
import com.example.schake.trip_peer.UrlaubFoto;
import com.example.schake.trip_peer.UrlaubUebersicht;

public class ArchivedTripListClickHandler implements View.OnClickListener {

    Long tripId;
    public ArchivedTripListClickHandler(Long tripId) {
        this.tripId = tripId;
    }

    @Override
    public void onClick(View v)
    {
       Intent showPicturesIntent = new Intent(v.getContext(), UrlaubUebersicht.class );
        showPicturesIntent.putExtra("tripId", this.tripId );
        v.getContext().startActivity( showPicturesIntent );
    }


}
