package com.example.schake.trip_peer.Data;

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

    private Long currentTripId = null;

    private static AtomicReference<Long> currentTime =
            new AtomicReference<>(System.currentTimeMillis());


    /**
     * Hashmap with a key, value stores
     * Stores all Trips with a unique interger
     */
    private Map<Long, Trip> trips = new HashMap<Long, Trip>();

    protected TripManager() {
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
        this.trips.put(tripId , trip );

        saveToStorage();

        return tripId;
    }

    public void appendPhotoToActiveTrip( Photo photo) {
        if( this.currentTripId != null ) {
            this.getTripById( this.currentTripId ).addPhoto( photo );
        }
    }

    public Map<Long,Trip> getTrips() {
        return new HashMap<Long,Trip>( this.trips );
    }

    public Trip getTripById ( Long id ) {
        if( this.trips.containsKey(id) ){
            return this.trips.get(id);
        }else{
            return null;
        }
    }

    public void setActiveTrip( Long id ) {
        if( this.trips.containsKey( id ) ){
            this.currentTripId = id;
        }
    }

    public Long getCurrentTripId() {
        return this.currentTripId;
    }


    private void loadFromStorage() {

        try {


            File file = new File( TripPeerApplication.getAppContext().getFilesDir() , this.exportFileName);

            // if file doesn't exists, then create it
            if (!file.exists()) {
               return; //no file there
            }

            FileInputStream fileIn = new FileInputStream(file.getAbsoluteFile());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.trips.clear();
            this.trips.putAll(  (HashMap<Long, Trip>) in.readObject() );
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
            oos.writeObject(this.trips);
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
