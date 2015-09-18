package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.schake.trip_peer.Data.TripManager;
import com.example.schake.trip_peer.utils.MarkCurrentTripAsArchivedDialog;

public class Hauptmenu extends AppCompatActivity {


    Button newPictureButton;
    Button currentTripButton;
    Button newTripButton;
    Button latestTripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenu);

        newPictureButton = (Button) findViewById( R.id.weiteres_foto );
        currentTripButton = (Button) findViewById( R.id.aktueller_urlaub );
        newTripButton = (Button) findViewById( R.id.neuer_urlaub );
        latestTripButton = (Button) findViewById(R.id.archivierte_urlaube );

        checkButtons();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkButtons();
    }



    private void checkButtons() {

        TripManager manager = TripManager.getInstance();

        currentTripButton.setEnabled( manager.hasActiveTrip() );
        newPictureButton.setEnabled( manager.hasActiveTrip() );

        latestTripButton.setEnabled(manager.getTrips().size() > 0);

    }

    public void newPicture( View view ) {
        Intent newPictureIntent = new Intent(this, FotoNeu.class );
        //newPictureIntent.setFlags(newPictureIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(newPictureIntent);
    }

    public void currentTrip( View view ) {
        Intent newPictureIntent = new Intent(this, UrlaubAktuell.class );
        startActivity( newPictureIntent );
    }

    public void newTrip( View v ) {
        TripManager manager = TripManager.getInstance();
        if( manager.hasActiveTrip() ) {
            MarkCurrentTripAsArchivedDialog alertDialog = new MarkCurrentTripAsArchivedDialog();

            alertDialog.show(  alertDialog.getFragmentManager(), "HINT");

        }

        Intent newPictureIntent = new Intent(this, UrlaubNeu.class);
        startActivity( newPictureIntent );
    }

    public void latestTrips( View view ) {
        Intent newPictureIntent = new Intent(this, UrlaubArchiviert.class );
        startActivity( newPictureIntent );
    }
}
