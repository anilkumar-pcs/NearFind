package com.example.anilkumar.askmeanything;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends AppCompatActivity {

    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    // GPS Location
    GPSTracker gps;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        isInternetPresent = cd.isConnectingToInternet();
        if (!isInternetPresent) {
            // Internet Connection is not present
            //alert.showAlertDialog(StartPage.this, "Internet Connection Error",
            //        "Please connect to working Internet connection", false);

            Snackbar snackbar = Snackbar
                    .make((CoordinatorLayout) findViewById(R.id.coordLayout), "No Internet :(", Snackbar.LENGTH_INDEFINITE)
                    .setAction("REFRESH", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Refresh Same Activity
                            finish();
                            startActivity(getIntent());
                        }
                    });

            snackbar.show();

            // stop executing code by return
            return;
        }
        // creating GPS Class object
        gps = new GPSTracker(this);

        // check if GPS location can get
        if (gps.canGetLocation()) {
            Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
        } else {
            // Can't get user's current location
            //alert.showAlertDialog(StartPage.this, "GPS Status",
            //        "Couldn't get location information. Please enable GPS", false);

            Snackbar snackbar = Snackbar
                    .make((CoordinatorLayout) findViewById(R.id.coordLayout), "Location disabled :(", Snackbar.LENGTH_INDEFINITE)
                    .setAction("ENABLE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                        }
                    });

            snackbar.show();

            // stop executing code by return
            return;
        }

        final AutoCompleteTextView search = (AutoCompleteTextView)findViewById(R.id.searchText);
        String[] places = getResources().getStringArray(R.array.google_place_types);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places);
        search.setAdapter(adapter);

        Button enter = (Button)findViewById(R.id.search);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search.getText().toString().trim();
                if(query.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter valid Input", Toast.LENGTH_SHORT);
                } else {
                    MainActivity.types = query;
                    Intent intent = new Intent(StartPage.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //when search icon pressed on keyboard
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            String query = search.getText().toString().trim();

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (query.isEmpty()) {
                        Toast.makeText(getApplicationContext(),"Enter valid Input",Toast.LENGTH_SHORT);
                    }
                    else {
                        MainActivity.types = query;
                        Intent intent = new Intent(StartPage.this, MainActivity.class);
                        startActivity(intent);
                        handled = true;
                    }
                }
                return handled;
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
