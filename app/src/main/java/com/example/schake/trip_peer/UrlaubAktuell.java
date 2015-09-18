package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.schake.trip_peer.Data.Photo;
import com.example.schake.trip_peer.Data.Trip;
import com.example.schake.trip_peer.Data.TripManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlaubAktuell extends  FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Map<Marker, Integer> markerList = new HashMap<Marker, Integer>();
    private Long currentTripId = (long)0;

    @Override
    public void onMapReady(GoogleMap map) {
        markerList.clear();
        TripManager manager = TripManager.getInstance();
        currentTripId = manager.getCurrentTrip().getTripId();
        List<Photo> pictures = manager.getCurrentTrip().getPhotos();


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
                    .title(pic.getComment()));

            this.markerList.put(markerAdded, counter);

            //add new point for line
            rectOptions.add(pic.getGpsPoint());

            counter++;
        }

        // Get back the mutable Polyline
        Polyline polyline = map.addPolyline(rectOptions);

        map.setOnMarkerClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_aktuell);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        int counter = this.markerList.get( marker );

        Intent showPictureIntent = new Intent( this, UrlaubFoto.class);
        showPictureIntent.putExtra("index", counter);
        showPictureIntent.putExtra("tripId", currentTripId);

        startActivity(showPictureIntent);

        return true;
    }


    public void showMenu( View view ) {
        Intent newMenuIntent = new Intent(this, Hauptmenu.class );
        startActivity(newMenuIntent);
    }

    public void saveCurrentTrip(View view) {
        TripManager manager = TripManager.getInstance();
        manager.archivCurrentTrip();
        Intent showArchivedTrips = new Intent(this, UrlaubArchiviert.class );
        startActivity( showArchivedTrips );
    }
}
