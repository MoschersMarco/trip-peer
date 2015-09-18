package com.example.schake.trip_peer.Data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mmoscher on 17.09.2015.
 */
public class Photo implements Comparable<Photo>, Serializable {

    private String comment;
    private String fileName;
    private String filePath;

    private LatLng gpsPoint;

    private Date createdAt;

    public Photo() {
        this.createdAt = new Date();
    }

    public Photo( LatLng gpsPoint, String fileName ) {
        this();
        setGpsPoint( gpsPoint );
        setFileName( fileName );
    }

    @Override
    public int compareTo(Photo o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LatLng getGpsPoint() {
        return gpsPoint;
    }

    public void setGpsPoint(LatLng gpsPoint) {
        this.gpsPoint = gpsPoint;
    }
}
