    package com.example.schake.trip_peer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.schake.trip_peer.Data.Trip;
import com.example.schake.trip_peer.Data.TripManager;

public class UrlaubNeu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_neu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_urlaub_neu, menu);
        return true;
    }

    public void createTrip(View view) {
        TripManager manager = TripManager.getInstance();

        EditText urlaubsName = (EditText) findViewById(R.id.inputUrlaubName);

        Long newTripId = manager.newTrip( urlaubsName.getText().toString() );


        Intent newPictureIntent = new Intent(this, FotoNeu.class );
        startActivity( newPictureIntent );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
