    package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.schake.trip_peer.Data.Trip;
import com.example.schake.trip_peer.Data.TripManager;

public class UrlaubNeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_neu);
    }
// Neuer Urlaub wird erstellt und TripManager verwaltet das Datenmodell.
    public void createTrip(View view) {
        TripManager manager = TripManager.getInstance();

        EditText urlaubsName = (EditText) findViewById(R.id.inputUrlaubName);

        Long newTripId = manager.newTrip( urlaubsName.getText().toString() );

/* Durch das Drücken der Taste "Urlaub starten" wird man automatisch weitergeleitet
und kann ein erstes Foto machen.*/

        Intent newPictureIntent = new Intent(this, FotoNeu.class );
        startActivity( newPictureIntent );
    }

}
