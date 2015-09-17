package com.example.schake.trip_peer;

import android.app.Application;
import android.content.Context;

public class TripPeerApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        TripPeerApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return TripPeerApplication.context;
    }
}
