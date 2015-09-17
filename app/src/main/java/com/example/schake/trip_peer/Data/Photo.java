package com.example.schake.trip_peer.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mmoscher on 17.09.2015.
 */
public class Photo implements Comparable<Photo>, Serializable {


    private long gpsLat;
    private long gpsLong;

    private String comment;
    private String fileName;
    private String filePath;


    private Date createdAt;

    public Photo() {
        this.createdAt = new Date();
    }

    public Photo( long gpsLat, long gpsLong, String fileName ) {
        this();

        setGpsLat(gpsLat);
        setGpsLong( gpsLong );
        setFileName( fileName );
    }

    @Override
    public int compareTo(Photo o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }


    public long getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(long gpsLat) {
        this.gpsLat = gpsLat;
    }

    public long getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(long gpsLong) {
        this.gpsLong = gpsLong;
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

}
