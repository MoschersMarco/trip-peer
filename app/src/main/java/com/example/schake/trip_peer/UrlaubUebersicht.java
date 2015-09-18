package com.example.schake.trip_peer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.schake.trip_peer.Data.Photo;
import com.example.schake.trip_peer.Data.TripManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlaubUebersicht extends FragmentActivity
            implements OnMapReadyCallback, OnMarkerClickListener {

    private Map<Marker, Integer> markerList = new HashMap<Marker, Integer>();
    private Long currentTripId = (long)0;

    @Override
    public void onMapReady(GoogleMap map) {
        markerList.clear();
        TripManager manager = TripManager.getInstance();
        currentTripId = getIntent().getLongExtra("tripId", 0);
        List<Photo> pictures = manager.getTripById( currentTripId ).getPhotos();

        // Instantiates a new Polyline object and adds points to define a rectangle
        PolylineOptions rectOptions = new PolylineOptions();
        Integer counter = 0;
        for( Photo pic : pictures ) {

            //legacy
            if( pic.getGpsPoint() == null ) {
                continue;
            }

            //add marker
            Marker markerAdded = map.addMarker(new MarkerOptions()
                    .position( pic.getGpsPoint())
                    .title(counter.toString())
                    .title(pic.getComment()));

            this.markerList.put( markerAdded, counter );

            //add new point for line
            rectOptions.add(pic.getGpsPoint());

            counter++;
        }

        // Get back the mutable Polyline
        Polyline polyline = map.addPolyline(rectOptions);

        map.setOnMarkerClickListener(this);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        int counter = this.markerList.get( marker );

        Intent showPictureIntent = new Intent( this, UrlaubFoto.class);
        showPictureIntent.putExtra("index", counter);
        showPictureIntent.putExtra("tripId", currentTripId);

        startActivity(showPictureIntent );

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_uebersicht);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void showMenufromUebersicht( View view) {
        Intent newMenuIntent = new Intent(this, Hauptmenu.class );
        startActivity(newMenuIntent);
    }

    public void showArchiv( View view ) {
        Intent newMenuIntent = new Intent(this, UrlaubArchiviert.class );
        startActivity(newMenuIntent);
    }

}
