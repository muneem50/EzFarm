package com.example.mygui1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn;





        btn = findViewById(R.id.btn);

        EditText nc, pc, kc, tc, hc, phc, rc;

        nc = findViewById(R.id.nc);
        pc = findViewById(R.id.pc);
        kc = findViewById(R.id.kc);
        tc = findViewById(R.id.tc);
        hc = findViewById(R.id.hc);
        phc = findViewById(R.id.phc);
        rc = findViewById(R.id.rc);

        Button btnClear = findViewById(R.id.btn_clear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all EditText values
                nc.setText("");
                pc.setText("");
                kc.setText("");
                tc.setText("");
                hc.setText("");
                phc.setText("");
                rc.setText("");
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double phcc, tcc , hcc, rcc, ncc, pcc, kcc;
                ncc = nc.getText().toString().trim().isEmpty() ? 0.0 : Double.parseDouble(nc.getText().toString().trim());
                pcc = pc.getText().toString().trim().isEmpty() ? 0.0 : Double.parseDouble(pc.getText().toString().trim());
                kcc = kc.getText().toString().trim().isEmpty() ? 0.0 : Double.parseDouble(kc.getText().toString().trim());
                hcc = Double.parseDouble(hc.getText().toString().trim());
                tcc = Double.parseDouble(tc.getText().toString().trim());
                phcc = phc.getText().toString().trim().isEmpty() ? 0.0 :Double.parseDouble(phc.getText().toString().trim());
                rcc = rc.getText().toString().trim().isEmpty() ? 0.0 :Double.parseDouble(rc.getText().toString().trim());


                if (ncc == 0.0 || pcc == 0.0 || kcc == 0.0 || phcc == 0.0 || rcc == 0.0) {
                    Toast.makeText(MainActivity.this, "Please fill in all nutrient values", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method if any input is empty
                }

                //  proceed to start the Predictionpage activity
                Intent i2 = new Intent(MainActivity.this, Predictionpage.class);
                i2.putExtra("Nitrogen Content", ncc);
                i2.putExtra("Phosphorus Content", pcc);
                i2.putExtra("Potassium Content", kcc);
                i2.putExtra("Temperature", tcc);
                i2.putExtra("Humidity", hcc);
                i2.putExtra("pH of soil", phcc);
                i2.putExtra("Rainfall", rcc);

                startActivity(i2);
            }
        });
    }
}

