package com.example.schake.trip_peer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.schake.trip_peer.Data.Photo;
import com.example.schake.trip_peer.Data.TripManager;
import com.example.schake.trip_peer.utils.PictureLocationListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoNeu extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    private ImageView kamera;
    private Uri fileUri;

    private Button saveImageButton;
    private Button saveImageAndArchivTripButton;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private PictureLocationListener locListener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_neu);
        kamera = (ImageView) findViewById(R.id.kamera);
        saveImageButton = (Button) findViewById(R.id.urlaub_fortsetzen);
        saveImageAndArchivTripButton = (Button) findViewById(R.id.urlaub_archivieren);

        saveImageButton.setEnabled( false );
        saveImageAndArchivTripButton.setEnabled( false );

        LocationManager mlocManager = (LocationManager)getSystemService(TripPeerApplication.getAppContext()
                .LOCATION_SERVICE);

        // Hier werden die GPS Daten ermittelt.
        boolean isGPSEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        locListener= new PictureLocationListener();
        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locListener);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

        //try to get a last known location
        if (mlocManager != null && (isGPSEnabled||isNetworkEnabled) ) {
            Location location = null;
        // Zuerst werden GPS Daten abgefragt.
            if( isGPSEnabled ) {
                location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    locListener.setLastLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            }
            // Falls keine GPS Daten vorhanden, werden mögliche Standorte über den Netzwerkprovider bezogen.
            if( isNetworkEnabled &&location == null) {
                //try to get network attached address
                location = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if( location != null ){
                    locListener.setLastLocation( new LatLng(location.getLatitude(), location.getLongitude()) );
                }
            }

        }

    // Weiterleitung zu den GPS Einstellungen, falls GPS nicht eingeschaltet ist.
        if( !isGPSEnabled ){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

    }

    public void takeNewPicture( View view ) {
        // Durch das Drücken dieses Knopfes, gelangt man zu der Kamera und ein Foto kann gemacht werden.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    // Nutzer entscheidet ob das Foto ausgewählt wird oder ein neues gemacht werden soll.
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                kamera.setImageURI( fileUri );
                saveImageButton.setEnabled( true );
                saveImageAndArchivTripButton.setEnabled( true );
            }
        }

    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
// Hier wird das Bild gespeichert und man gelangt zurück in das Hauptmenü.
    public void continueTrip( View view ){
        if( saveImage () ) {
            showMainMenu();
        }
    }
// Hier wird das letzte Foto des Urlaubens gespeichert, der Urlaub wird beendet und archiviert.
    public void archivTrip( View view ) {
        if( saveImage() ) {

            TripManager manager = TripManager.getInstance();
            manager.archivCurrentTrip();
            showMainMenu();
        }
    }

    public void showMainMenu() {
        Intent backToMainMenu = new Intent( this, Hauptmenu.class );
        startActivity(backToMainMenu);
    }

    public boolean saveImage() {

        EditText commentTextField = (EditText) findViewById(R.id.kommentar_zum_foto);
        String comment = commentTextField.getText().toString();

        File picture = new File(fileUri.getPath());
        LatLng position = this.locListener.getLastLocation();
    /* Falls letzte bekannte Location unbekannt ist, wird eine Information angezeigt, die besagt,
        dass keine Daten Über den Standort verfügbar sind.*/
        if(position == null ) {
            Toast.makeText( TripPeerApplication.getAppContext(),
                    "GPS Location is unkown - please wait",
                    Toast.LENGTH_LONG ).show();
            return false;
        }else {


            TripManager manager = TripManager.getInstance();
// Hier wird das Foto erstellt und gespeichert.
            Photo newPicture = new Photo();
            newPicture.setComment(comment);
            newPicture.setFilePath(picture.getAbsolutePath());
            newPicture.setFileName(picture.getName());
            newPicture.setGpsPoint( position );

            manager.appendPhotoToActiveTrip(newPicture);

            return true;
        }
    }

}
