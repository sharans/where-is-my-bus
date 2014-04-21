package com.example.WhereIsMyBus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.WhereIsMyBus.datastore.DbHelper;

public class MainActivity extends Activity  {
    /**
     * Called when the activity is first created.
     */
    private DbHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DbHelper(this);

        final Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(save);
    }

    View.OnClickListener save = new View.OnClickListener(){
        public void onClick(View v) {
            EditText source = (EditText) findViewById(R.id.source_text);
            EditText destination = (EditText) findViewById(R.id.destination_text);
            EditText busNumber = (EditText) findViewById(R.id.bus_num_text);
            db.insertOrUpdateBusDetails(source.getText().toString(), destination.getText().toString(), busNumber.getText().toString());
            Intent loadMap = new Intent(MainActivity.this, ViewMapActivity.class);
            startActivity(loadMap);
        }
    };
}
