package com.example.mygui1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Predictionpage extends AppCompatActivity {
    String url="https://apio-fuhk.onrender.com/predict1";
    String ans="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictionpage);

        Intent p= getIntent();
        double pooo=0.0;

        double nitrogen = p.getDoubleExtra("Nitrogen Content", 0.0);
        double phosphorus =  p.getDoubleExtra("Phosphorus Content", 0.0);
        double  potassium =  p.getDoubleExtra("Potassium Content", 0.0);
        double temp =  p.getDoubleExtra("Temperature", 0.0);
        double humid = p.getDoubleExtra("Humidity", pooo);
        double ph =  p.getDoubleExtra("pH of soil", 0.0);
        double rain =  p.getDoubleExtra("Rainfall", 0.0);

        TextView res;
        res=findViewById(R.id.result);
        res.setTextColor(Color.parseColor("#0DC8B2"));
        res.setShadowLayer(0.01f, -2, 2, getResources().getColor(R.color.text_shadow_color));
        StringRequest req=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject josnObject=new JSONObject(response);
                            res.setText(josnObject.getString("result").toString());
                        } catch (JSONException e) {
                            res.setText("APP Error");
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                res.setText("Server Error");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> par=new HashMap<String,String>();
                par.put("nc",String.valueOf(nitrogen));
                par.put("pc",String.valueOf(phosphorus));
                par.put("kc",String.valueOf(potassium));
                par.put("ph",String.valueOf(ph)) ;
                par.put("temp",String.valueOf( temp)) ;
                par.put("humidity",String.valueOf(humid)) ;
                par.put("rain",String.valueOf(rain)) ;
                return par;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(Predictionpage.this);
        queue.add(req);

        TextView a, b, c, d, e, f, g;
        a = findViewById(R.id.a);
        a.setText("Nitrogen Content in soil : "+nitrogen );

        b = findViewById(R.id.b);
        b.setText("Phosphorus Content in soil : "+phosphorus);

        c = findViewById(R.id.c);
        c.setText("Potassium Content in soil : "+potassium);

        d = findViewById(R.id.d);
        d.setText("Temperature : "+temp);

        e = findViewById(R.id.e);
        e.setText("Humidity : "+humid);

        f = findViewById(R.id.f);
        f.setText("Soil pH : "+ph);

        g = findViewById(R.id.g);
        g.setText("Rainfall : "+rain);


    }
}