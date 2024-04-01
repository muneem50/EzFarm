package com.example.mygui1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splashh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashh);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent s = new Intent(Splashh.this, location.class);
                startActivity(s);
            }
        }, 3000);
    }
}