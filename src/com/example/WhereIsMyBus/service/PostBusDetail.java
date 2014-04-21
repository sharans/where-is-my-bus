package com.example.WhereIsMyBus.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.WhereIsMyBus.BusDetails;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PostBusDetail extends AsyncTask<BusDetails, Void, Void> {
    private final Context mContext;

    public PostBusDetail(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(BusDetails... busDetails) {
        BusDetails busDetail = busDetails[0];
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.16.3.16:3000/board/"+ busDetail.getBusNumber());
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("source", busDetail.getSource()));
            nameValuePairs.add(new BasicNameValuePair("destination", busDetail.getDestination()));
            String lat = String.valueOf(busDetail.getLocation().getLatitude());
            String lng = String.valueOf(busDetail.getLocation().getLongitude());
            nameValuePairs.add(new BasicNameValuePair("lat", lat));
            nameValuePairs.add(new BasicNameValuePair("lng", lng));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
        } catch (Exception e) {
            Log.d(null, "Error on posting bus details");
        }
        return null;
    }

    protected void onPostExecute() {
        Toast.makeText(mContext, "Bus Details updated!", Toast.LENGTH_LONG);
    }
}
