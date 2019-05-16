package com.example.fire.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fire.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeviceState extends AppCompatActivity {


    private String username;
    private String local;
    private int token;
    private EditText eTTemperature;
    private EditText eTHumidity;
    private Button showFiresB;
    private CheckBox checkBoxAlarm;
    private int humidity;
    private int temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_state);

        eTTemperature = findViewById(R.id.temperatureBox);
        eTHumidity = findViewById(R.id.humidityBox);
        showFiresB = findViewById(R.id.showFiresButton);
        checkBoxAlarm = findViewById(R.id.checkBoxAlarm);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        token = intent.getIntExtra("token", 0);
        local = intent.getStringExtra("local");


        getSupportActionBar().setTitle("Local: " + local);

        //Faz POST com username e token e recebe o o valor da temperatura e humidade e do alarme
        humidity = 40;
        temperature = 35;

        eTTemperature.setText(Float.toString(temperature));
        eTHumidity.setText(Float.toString(humidity));


        showFiresB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeviceState.this, "Showing Fires", Toast.LENGTH_LONG).show();
            }
        });

        checkBoxAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked) {
                    Toast.makeText(DeviceState.this, "Alarm enabled", Toast.LENGTH_LONG).show();
                    Log.d("tag", "Alarm enabled");
                    //Do POST
                } else {
                    Toast.makeText(DeviceState.this, "Alarm disabled", Toast.LENGTH_LONG).show();
                    Log.d("tag", "Alarm disabled");
                    //Do POST
                }
            }
        });
    }
}
