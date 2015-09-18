package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.schake.trip_peer.Data.TripListViewAdapter;
import com.example.schake.trip_peer.Data.TripManager;

public class UrlaubArchiviert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_archiviert);

        TripManager manager = TripManager.getInstance();

        ListView listView = (ListView) findViewById( R.id.archivierteUrlaubeListView );

        TripListViewAdapter adapter = new TripListViewAdapter( manager.getTrips() );
        listView.setAdapter( adapter );
    }

    public void showMenufromArchiv( View view ) {
        Intent newMenuIntent = new Intent(this, Hauptmenu.class );
        startActivity(newMenuIntent);
    }



}
