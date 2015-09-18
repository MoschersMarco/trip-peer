package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Hauptmenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenu);
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
        Intent newPictureIntent = new Intent(this, UrlaubNeu.class );
        startActivity( newPictureIntent );
    }

    public void latestTrips( View view ) {
        Intent newPictureIntent = new Intent(this, UrlaubArchiviert.class );
        startActivity( newPictureIntent );
    }
}
