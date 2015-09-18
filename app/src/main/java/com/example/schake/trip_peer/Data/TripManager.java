package com.example.schake.trip_peer.Data;

import android.util.Log;

import com.example.schake.trip_peer.TripPeerApplication;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Manager class with Singletondesign pattern
 */
public class TripManager {


    private final String exportFileName = "data-storage.trippeer";

    private static TripManager instance= null;

    private Trip currentTrip = null;

    private static AtomicReference<Long> currentTime =
            new AtomicReference<>(System.currentTimeMillis());

    private int counter = 0;


    /**
     * Hashmap with a key, value stores
     * Stores all Trips with a unique interger
     */
    private Map<Long, Trip> archivedTrips = new HashMap<Long, Trip>();

    protected TripManager() {
    }


    public void archivCurrentTrip() {
        this.archivedTrips.put( currentTrip.getTripId(), currentTrip );
        this.currentTrip = null;

        saveToStorage();
    }

    public boolean hasActiveTrip() {
        return ( this.currentTrip != null );
    }

    public static TripManager getInstance() {
        if( TripManager.instance == null ) {
            TripManager.instance = new TripManager();
            TripManager.instance.loadFromStorage();
        }
        return TripManager.instance;
    }

    public Long newTrip( String tripName ) {
        Trip trip = new Trip( tripName );
        Long tripId = TripManager.nextId();
        trip.setTripId( tripId );

        this.currentTrip = trip;

        saveToStorage();

        return tripId;
    }

    public void appendPhotoToActiveTrip( Photo photo) {
        if( this.currentTrip != null ) {

            this.currentTrip.addPhoto( photo);
            saveToStorage();
        }
    }

    public Map<Long,Trip> getTrips() {
        return new HashMap<Long,Trip>( this.archivedTrips );
    }

    public Trip getTripById ( Long id ) {
        if( this.archivedTrips.containsKey(id) ){
            return this.archivedTrips.get(id);
        }else{
            if( currentTrip.getTripId().equals(id) ) {
                return currentTrip;
            }
        }

        return null;
    }

    public Trip getCurrentTrip() {
        return this.currentTrip;
    }

    private void loadFromStorage() {

        this.counter = 0;

        try {

            File file = new File( TripPeerApplication.getAppContext().getFilesDir() , this.exportFileName);

            // if file doesn't exists, then create it
            if (!file.exists()) {
               return; //no file there
            }

            FileInputStream fileIn = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.archivedTrips.clear();
            TripExport loaded = (TripExport)in.readObject();
            this.archivedTrips.putAll(  loaded.getArchivedTrips() );
            this.currentTrip = loaded.getCurrentTrip();

            in.close();
            fileIn.close();

        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }



    }

    private void saveToStorage() {

        try {

            File file = new File( TripPeerApplication.getAppContext().getFilesDir(), this.exportFileName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


            FileOutputStream fout = new FileOutputStream( file.getAbsoluteFile() );
            ObjectOutputStream oos = new ObjectOutputStream(fout);

            TripExport export = new TripExport( this.currentTrip, this.archivedTrips );
            oos.writeObject(export);
            oos.close();

        }catch( IOException e ) {

        }
    }



    /**
     * Helper method to generate a unique long id for each trip
     * @return
     */
    public static Long nextId() {
        Long prev;
        Long next = System.currentTimeMillis();
        do {
            prev = currentTime.get();
            next = next > prev ? next : prev + 1;
        } while (!currentTime.compareAndSet(prev, next));
        return next;
    }


}
