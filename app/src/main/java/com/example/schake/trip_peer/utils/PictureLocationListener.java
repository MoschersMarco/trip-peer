package com.example.schake.trip_peer.utils;

/* Class My Location Listener */

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.example.schake.trip_peer.TripPeerApplication;

public class PictureLocationListener implements LocationListener {



    @Override
    public void onLocationChanged(Location loc){

        loc.getLatitude();
        loc.getLongitude();
        String Text = "My current location is: "+
        "Latitud = " + loc.getLatitude() +
        "Longitud = " + loc.getLongitude();


        Toast.makeText(TripPeerApplication.getAppContext(),
                Text,
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onProviderDisabled(String provider){
        Toast.makeText( TripPeerApplication.getAppContext(),
                "Gps Disabled",
                Toast.LENGTH_SHORT ).show();

    }


    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText( TripPeerApplication.getAppContext(),
                "Gps Enabled",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){   }

}