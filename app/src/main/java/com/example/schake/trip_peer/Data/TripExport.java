package com.example.schake.trip_peer.Data;

import java.io.Serializable;
import java.util.Map;

public class TripExport implements Serializable {

    private Trip currentTrip;
    private Map<Long, Trip> archivedTrips;

    public TripExport( Trip current, Map<Long,Trip> archived ){
        this.currentTrip = current;
        this.archivedTrips = archived;
    }


    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    public Map<Long, Trip> getArchivedTrips() {
        return archivedTrips;
    }

    public void setArchivedTrips(Map<Long, Trip> archivedTrips) {
        this.archivedTrips = archivedTrips;
    }


}
