package com.example.ezfarming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private String selectedDistrict, selectedState;                 //vars to hold the values of State and District
    private TextView tvStateSpinner, tvDistrictSpinner;             //declaring TextView to show the errors
    private Spinner stateSpinner, districtSpinner;                  //Spinners
    private Spinner stateSpinnerCrop;
    private ArrayAdapter<CharSequence> stateAdapterCrop;



    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;   //adapters for the spinners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crop Spinner Initialization
        stateSpinnerCrop = findViewById(R.id.spinner_crop);
        // Populate ArrayAdapter using string array and a spinner layout that we will define
        stateAdapterCrop = ArrayAdapter.createFromResource(this,
                R.array.array_crop, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appears
        stateAdapterCrop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinnerCrop.setAdapter(stateAdapterCrop); // Set the adapter to the spinner to populate the State Spinner

        //States kay lia  Spinner
        stateSpinner = findViewById(R.id.spinner_states);    //Finds a view that was identified by the android:id attribute in xml

        //Populate ArrayAdapter using string array and a spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_states, R.layout.spinner_layout);

        // Specify the layout to use when the list of choices appear
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the adapter to the spinner to populate the State Spinner
        stateSpinner.setAdapter(stateAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtSpinner = findViewById(R.id.spinner_districts);

                //Obtain the selected State
                selectedState = stateSpinner.getSelectedItem().toString();

                int parentID = parent.getId();
                if (parentID == R.id.spinner_states){
                    switch (selectedState){
                        case "Select Your State": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_default_districts, R.layout.spinner_layout);
                            break;

                        case "Khyber Pakhtunkhwa": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_kpk_districts, R.layout.spinner_layout);
                            break;
                        case "Punjab": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_punjab_districts, R.layout.spinner_layout);
                            break;
                        case "Sindh": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_sindh_districts, R.layout.spinner_layout);
                            break;
                        case "Balochistan": districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                R.array.array_balochistan_districts, R.layout.spinner_layout);
                            break;

                        default:  break;
                    }
                    // Specify the layout to use when the list of choices appears
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // list of Districts in respect of the State selected
                    districtSpinner.setAdapter(districtAdapter);

                    //To obtain the selected District from the spinner
                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//Button wala kaam
        Button submitButton;
// //To switch between screens


        submitButton = findViewById(R.id.button_submit);
        tvStateSpinner = findViewById(R.id.textView_states);
        tvDistrictSpinner = findViewById(R.id.textView_districts);
        submitButton.setOnClickListener(v -> {

            if (selectedState.equals("Select Your State")) {
                Toast.makeText(MainActivity.this, "Please select your state from the list", Toast.LENGTH_LONG).show();
                tvStateSpinner.setError("required!");      //error on TextView
                tvStateSpinner.requestFocus();
            } else if (selectedDistrict.equals("Select Your District")) {
                Toast.makeText(MainActivity.this, "Please select your district from the list", Toast.LENGTH_LONG).show();
                tvDistrictSpinner.setError("required!");
                tvDistrictSpinner.requestFocus();
                tvStateSpinner.setError(null);
            } else {
                tvStateSpinner.setError(null);
                tvDistrictSpinner.setError(null);
                Toast.makeText(MainActivity.this, "Selected State: "+selectedState+"\nSelected District: "+selectedDistrict, Toast.LENGTH_LONG).show();
                Intent intent= new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });
    }
}
