package com.example.schake.trip_peer.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Trip implements Comparable<Trip>, Serializable {

    private List<Photo> photos;
    private String name;
    private Date createdAt;

    public Trip() {
        this.photos = new ArrayList<Photo>();
        this.createdAt = new Date();
    }

    public Trip( String name ) {
        this();
        setName( name );
    }



    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Photo> getPhotos() {
        Collections.sort(this.photos);
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto( Photo photo ) {
        this.photos.add(photo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Trip o) {
        return getCreatedAt().compareTo(o.getCreatedAt());
    }


}
