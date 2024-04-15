package com.example.mygui1;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.List;
import java.util.Locale;

public class location extends AppCompatActivity implements LocationListener {
//    private String selectedDistrict, selectedState;                 //vars to hold the values of selected State and District
//    private TextView tvStateSpinner, tvDistrictSpinner;             //declaring TextView to show the errors
//    private Spinner stateSpinner, districtSpinner;                  //Spinners

    Spinner spinner;
    public static final String[] Languages = {"Select Language", "English","Urdu"};

    private Spinner stateSpinnerCrop;
    private ArrayAdapter<CharSequence> stateAdapterCrop;
    LocationManager locationManager;
    TextView tvCity, tvState;
//    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;   //declare adapters for the spinners

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, Languages); adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {

                String selectedLang = parent.getItemAtPosition(position).toString();
                if (selectedLang.equals("English")){

                    setLocal( location.this,  "en"); finish();
                    startActivity(getIntent());

                }else if (selectedLang.equals("Urdu")) {
                    setLocal(  location.this,  "hi"); finish();
                    startActivity (getIntent());

                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {

            }
        });



    Button submitButton;
        TextView abt;
        abt = findViewById(R.id.abt);
        abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1;
                i1 = new Intent(location.this, Detailofapp.class);
                startActivity(i1);
            }
        });
        stateSpinnerCrop = findViewById(R.id.spinner_crop);
        stateAdapterCrop = ArrayAdapter.createFromResource(this, R.array.array_crop, R.layout.spinner_layout);
        stateAdapterCrop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinnerCrop.setAdapter(stateAdapterCrop); // Set the adapter to the spinner to populate the State Spinner

        submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(v -> {
            Intent intent = new Intent(location.this, MainActivity.class);
            startActivity(intent);

        });
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationEnabled();
        getLocation();
    }

    private void locationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(location.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("We need your GPS location to show Near Places around you.")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            tvCity.setText(addresses.get(0).getLocality());
            tvState.setText(addresses.get(0).getAdminArea());

        } catch (Exception e) {
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {


    }

    public void setLocal (Activity activity, String langCode){
        Locale locale = new Locale (langCode); locale.setDefault(locale);
        Resources resources = activity.getResources(); Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


}

