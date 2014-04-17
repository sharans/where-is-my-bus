package com.example.WhereIsMyBus;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.WhereIsMyBus.datastore.DbHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    DbHelper db;
    private GoogleMap googleMap;
    private LatLng myLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();
        Location currentLocation = getLocation();

        myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        myLocation = new LatLng(12.985407, 80.245641);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));

        db = new DbHelper(this);

        final Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(save);
        updateMarkerOnMap();
    }

    private Location getLocation(){
        Location currentLocation = null;
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(isNetworkEnabled && isGPSEnabled){
            currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }else{
            Toast.makeText(getApplicationContext(), "GPS/Network not enabled in device", Toast.LENGTH_LONG).show();
        }
        return currentLocation;
    }

    View.OnClickListener save = new View.OnClickListener(){
        public void onClick(View v) {
            EditText source = (EditText) findViewById(R.id.source_text);
            EditText destination = (EditText) findViewById(R.id.destination_text);
            EditText busNumber = (EditText) findViewById(R.id.bus_num_text);
            db.addRecord(source.getText().toString(), destination.getText().toString(), busNumber.getText().toString());
        }
    };

    private void updateMarkerOnMap() {
        MarkerOptions marker = new MarkerOptions().position(myLocation).title("Bus is here!");
        googleMap.addMarker(marker);
    }

}
