package com.example.WhereIsMyBus;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.WhereIsMyBus.datastore.DbHelper;
import com.example.WhereIsMyBus.service.GPSTracker;
import com.example.WhereIsMyBus.service.PostBusDetail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewMapActivity extends Activity {
    private GoogleMap googleMap;
    private LatLng myLocation;
    private DbHelper db;
    private GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        db = new DbHelper(this);

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                R.id.map)).getMap();
        gps = new GPSTracker(getApplicationContext());

        Location currentLocation = gps.getLocation();
        myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16));
        updateMarkerOnMap();

        final Button boardButton = (Button) findViewById(R.id.board_button);
        boardButton.setOnClickListener(board);

    }

    private void updateMarkerOnMap() {
        MarkerOptions marker = new MarkerOptions().position(myLocation).title("I am here!");
        googleMap.addMarker(marker);
    }

    View.OnClickListener board = new View.OnClickListener(){
        public void onClick(View v) {
            BusDetails busDetails = db.getBusDetails();
            busDetails.setLocation(gps.getLocation());
            PostBusDetail postBusDetail = new PostBusDetail(getApplicationContext());
            postBusDetail.execute(busDetails);

        }
    };



}
