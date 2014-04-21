package com.example.WhereIsMyBus.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {
    private final Context mContext;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters -->1 m

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 ; // 1 minute

    public GPSTracker(Context context) {
        this.mContext = context;
    }

    public Location getLocation(){
        Location currentLocation = null;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(isNetworkEnabled && isGPSEnabled){
            Log.d(null, "**************************************************");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Log.d(null, "########################################################");
            if (locationManager != null) {
                Log.d(null, "^^^^^^^^^^^^^   LOC MAN IS NOT NULL !! ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else{
                Log.d(null, "^^^^^^^^^^^^^   LOC MAN IS NULL !! :( ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            }
        }else{
            Toast.makeText(getApplicationContext(), "GPS/Network not enabled in device", Toast.LENGTH_LONG).show();
        }
        return currentLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
