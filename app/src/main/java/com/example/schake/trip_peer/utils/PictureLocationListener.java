package com.example.schake.trip_peer.utils;

/* Class My Location Listener */

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import com.example.schake.trip_peer.TripPeerApplication;
import com.google.android.gms.maps.model.LatLng;

public class PictureLocationListener implements LocationListener {


    public LatLng getLastLocation() {
        return lastLocation;
    }

    private LatLng lastLocation = null;



    @Override
    public void onLocationChanged(Location loc){

        loc.getLatitude();
        loc.getLongitude();
        this.lastLocation = new LatLng( loc.getLatitude(), loc.getLongitude() );
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