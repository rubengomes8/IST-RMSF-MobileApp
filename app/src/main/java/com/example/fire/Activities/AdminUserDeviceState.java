package com.example.fire.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fire.R;

public class AdminUserDeviceState extends AppCompatActivity {

    private int secret;
    private String username;
    private String local;
    private int id;

    private EditText eTTemperature;
    private EditText eTHumidity;
    private Button showFiresB;
    private CheckBox checkBoxAlarm;

    private int temperature;
    private int humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_state_admin);

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);
        username = intent.getStringExtra("username");
        id = intent.getIntExtra("id", 0);
        local = intent.getStringExtra("local");

        getSupportActionBar().setTitle(username + " - " + local);

        eTTemperature = findViewById(R.id.temperatureAdminBox);
        eTHumidity = findViewById(R.id.humidityAdminBox);
        showFiresB = findViewById(R.id.showFiresAdminButton);

        humidity = 40;
        temperature = 35;

        eTTemperature.setText(Float.toString(temperature));
        eTHumidity.setText(Float.toString(humidity));

        showFiresB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminUserDeviceState.this, "Showing Fires", Toast.LENGTH_LONG).show();
            }
        });


    }
}
