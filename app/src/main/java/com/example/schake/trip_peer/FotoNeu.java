package com.example.schake.trip_peer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.schake.trip_peer.Camera.CameraPreview;
import com.example.schake.trip_peer.Data.Photo;
import com.example.schake.trip_peer.Data.TripManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoNeu extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 10;
    private ImageView kamera;
    private Uri fileUri;


    public static final int MEDIA_TYPE_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_neu);
        kamera = (ImageView) findViewById(R.id.kamera);
    }

    public void takeNewPicture( View view ) {
        // create Intent to take a picture and return control to the calling application
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

    public void continueTrip( View view ){
        saveImage();

        showMainMenu();
    }

    public void archivTrip( View view ) {
        saveImage();

        TripManager manager = TripManager.getInstance();
        manager.archivCurrentTrip();

        showMainMenu();
    }

    public void showMainMenu() {
        Intent backToMainMenu = new Intent( this, Hauptmenu.class );
        startActivity(backToMainMenu);
    }

    public void saveImage() {

        EditText commentTextField = (EditText) findViewById(R.id.kommentar_zum_foto);
        String comment = commentTextField.getText().toString();

        File picture = new File(fileUri.getPath());


        TripManager manager = TripManager.getInstance();

        Photo newPicture = new Photo();
        newPicture.setComment( comment );
        newPicture.setFilePath(picture.getAbsolutePath());
        newPicture.setFileName( picture.getName() );

        manager.appendPhotoToActiveTrip( newPicture );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foto_neu, menu);
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
