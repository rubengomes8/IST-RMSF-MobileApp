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
    private String password;
    private String local;
    private int token;
    private EditText eTTemperature;
    private EditText eTHumidity;
    private EditText eTGas;
    private Button showFiresB;
    private CheckBox checkBoxAlarm;
    private int secret;
    private int alarmEnabled;

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_state);

        eTTemperature = findViewById(R.id.temperatureBoxDS);
        eTHumidity = findViewById(R.id.humidityBoxDS);
        eTGas = findViewById(R.id.gasBoxDS);


        showFiresB = findViewById(R.id.showFiresButton);
        checkBoxAlarm = findViewById(R.id.checkBoxAlarm);




        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        token = intent.getIntExtra("token", 0);
        local = intent.getStringExtra("local");
        secret = intent.getIntExtra("secret", 0);

        if(secret > 0)
        {
            //checkBoxAlarm.setChecked(true);
            checkBoxAlarm.setEnabled(false);
            showFiresB.setVisibility(View.GONE);
        }


        getSupportActionBar().setTitle("Local: " + local);

        //Faz POST com username e token e recebe o o valor da temperatura e humidade e do alarme

        //Fazer Post para receber as condições do device de um utilizador
        URL = "https://fire-240718.appspot.com/app/conditions/";
        //manda POST com username e recebe os seus devices
        final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", "onResponse: STRING " + response);

                if(!response.isEmpty())
                {
                    try
                    {
                        JSONObject object= new JSONObject(response);
                        eTTemperature.setText(Integer.toString(object.getInt("temperature")));
                        eTHumidity.setText(Integer.toString(object.getInt("humidity")));
                        eTGas.setText(Integer.toString(object.getInt("gas")));

                        //receber alarm enabled or disabled.
                        alarmEnabled = object.getInt("alarmEnable");
                        if(alarmEnabled == 1)
                        {
                            checkBoxAlarm.setChecked(true);
                        }
                        else
                        {
                            checkBoxAlarm.setChecked(false);
                        }
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    //Do Nothing
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeviceState.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username);
                parameters.put("password", password);
                parameters.put("token", Integer.toString(token));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(DeviceState.this);
        rQueue.add(request);



        showFiresB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DeviceState.this, "Showing Fires", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(DeviceState.this, Fires.class);
                intent1.putExtra("username", username);
                intent1.putExtra("password", password);
                intent1.putExtra("token", token);
                intent1.putExtra("secret", secret);
                startActivity(intent1);

            }
        });

        checkBoxAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();


                if (checked) {
                    Toast.makeText(DeviceState.this, "Alarm enabled", Toast.LENGTH_LONG).show();
                    Log.d("tag", "Alarm enabled");

                    //Fazer Post para atualizar o valor do alarme para ativo
                    URL = "https://fire-240718.appspot.com/app/alarm/";
                    //manda POST com username e recebe os seus devices
                    final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", "onResponse: STRING " + response);

                            if(!response.isEmpty())
                            {
                                try
                                {
                                    JSONObject object= new JSONObject(response);


                                }
                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                //Do Nothing
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DeviceState.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("alarmEnable", "1");
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(DeviceState.this);
                    rQueue.add(request);


                } else {
                    Toast.makeText(DeviceState.this, "Alarm disabled", Toast.LENGTH_LONG).show();
                    Log.d("tag", "Alarm disabled");
                    //Do POST

                    //Fazer Post para atualizar o valor do alarme para ativo
                    URL = "https://fire-240718.appspot.com/app/alarm/";
                    //manda POST com username e recebe os seus devices
                    final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", "onResponse: STRING " + response);

                            if(!response.isEmpty())
                            {
                                try
                                {
                                    JSONObject object= new JSONObject(response);


                                }
                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                //Do Nothing
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DeviceState.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("alarmEnable", "0"); //desativa
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(DeviceState.this);
                    rQueue.add(request);
                }


            }
        });
    }
}
