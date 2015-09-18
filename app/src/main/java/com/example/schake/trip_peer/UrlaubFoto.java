package com.example.schake.trip_peer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.schake.trip_peer.Data.Photo;
import com.example.schake.trip_peer.Data.Trip;
import com.example.schake.trip_peer.Data.TripManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UrlaubFoto extends AppCompatActivity {

    private List<Photo> tripPhotos = new ArrayList<Photo>();
    private Long tripId = (long)0;
    private int currentIndex = 0;

    private Button next;
    private Button prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_foto);

        next = (Button)findViewById(R.id.naechstes_foto);
        prev = (Button)findViewById(R.id.vorherirges_foto);

        Intent callerIntent = getIntent();
        TripManager manager = TripManager.getInstance();

        tripId = callerIntent.getLongExtra("tripId", 0);

        Trip viewedTrip = manager.getTripById( tripId );
        tripPhotos.clear();
        this.currentIndex = callerIntent.getIntExtra("index", 0);

        tripPhotos.addAll( viewedTrip.getPhotos() );

        if( currentIndex > this.tripPhotos.size()-1  || currentIndex < 0) {
            currentIndex =0;
        }

        checkButtons();

        showPhoto( this.tripPhotos.get(this.currentIndex) );

    }

    private void checkButtons () {
        if( currentIndex + 1 == this.tripPhotos.size() ) {
            next.setEnabled( false );
        }else{
            next.setEnabled( true );
        }

        if( currentIndex == 0) {
            prev.setEnabled( false );
        }else{
            prev.setEnabled( true );
        }
    }

    public void showUebersicht( View view ) {
        Intent newMenuIntent = new Intent(this, UrlaubUebersicht.class );
        newMenuIntent.putExtra("tripId", tripId);
        startActivity(newMenuIntent);
    }

    private void showPhoto( Photo currentPhoto ) {

        File file = new File( currentPhoto.getFilePath() );

        TextView commentTextField = (TextView) findViewById( R.id.kommentar_foto_xy);
        commentTextField.setText(currentPhoto.getComment());

        ImageView picture = (ImageView) findViewById(R.id.pictures);

        picture.setImageURI(Uri.fromFile(file));
    }

    public void showNextPictureButtonClick( View view ) {
        this.currentIndex++;
        checkButtons();
        showPhoto( this.tripPhotos.get( this.currentIndex ) );
    }

    public void showPreviousPictureButtonClick( View view ) {
        this.currentIndex--;
        checkButtons();
        showPhoto( this.tripPhotos.get( this.currentIndex ) );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_urlaub_foto, menu);
        return true;
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
