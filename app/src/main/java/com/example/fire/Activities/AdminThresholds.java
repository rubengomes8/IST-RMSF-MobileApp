package com.example.fire.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Float;
import java.util.HashMap;
import java.util.Map;

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


public class AdminThresholds extends AppCompatActivity {

    private EditText eTTemperature;
    private EditText eTHumidity;
    private EditText eTgas;

    private Button apply;
    private int humidityTh;
    private int temperatureTh;
    private int gasTh;

    private String URL;
    private int secret;
    private boolean valid;
    private int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_thresholds);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eTTemperature = findViewById(R.id.temperatureBox);
        eTHumidity = findViewById(R.id.humidityBox);
        eTgas = findViewById(R.id.gasBox);

        apply = findViewById(R.id.applyButton);
        getSupportActionBar().setTitle("Thresholds");

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);
        temperatureTh = intent.getIntExtra("tTh", 0);
        humidityTh = intent.getIntExtra("hTh", 0);
        gasTh = intent.getIntExtra("cTh", 0);

        Log.d("setText", Integer.toString(temperatureTh));
        Log.d("setText", Integer.toString(humidityTh));
        Log.d("setText", Integer.toString(gasTh));

        eTTemperature.setText(Integer.toString(temperatureTh));
        eTHumidity.setText(Integer.toString(humidityTh));
        eTgas.setText(Integer.toString(gasTh));



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                humidityTh = Integer.valueOf(eTHumidity.getText().toString());
                temperatureTh = Integer.valueOf(eTTemperature.getText().toString());
                gasTh = Integer.valueOf(eTgas.getText().toString());



                //Verificar ranges das grandezas
                //Range do sensor da temperatura: 0 - 50ºC -> valor minimo 10 e valor maximo 40
                //Range do sensor de humidade relativa: 20% a 90% -> valor minimo 30 e valor maximo 80

                if(temperatureTh >= 10 && temperatureTh <= 40 && humidityTh >= 30 && humidityTh <= 80 && gasTh >= 0 && gasTh <= 99)
                    valid = true;
                else
                    valid = false;

                if(valid)
                {
                    //Fazer Post para alterar os thresholds
                    URL = "https://fire-240718.appspot.com/admin/thresholds/"; //URL do admin
                    //tenho de enviar temperature, humidity, e secret

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", "onResponse: STRING " + response);

                            if(!response.isEmpty())
                            {
                                try
                                {

                                    JSONObject object = new JSONObject(response);
                                    check = object.getInt("check");
                                    if( check == 1)
                                    {
                                        Toast.makeText(AdminThresholds.this, "Changes applied", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(AdminThresholds.this, "Some error occured", Toast.LENGTH_LONG).show();

                                    }
                                    Intent intent2 = new Intent(AdminThresholds.this, MenuAdmin.class);
                                    startActivity(intent2);

                                }
                                catch(JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                //Do Nothing
                                Toast.makeText(AdminThresholds.this, "Some error occured", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(AdminThresholds.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            //Envia sempre os parâmetros do post em string
                            parameters.put("secret", Integer.toString(secret)); //secret
                            parameters.put("humidity", Integer.toString(humidityTh));//humidity
                            parameters.put("temperature", Integer.toString(temperatureTh));//temperature
                            parameters.put("gas", Integer.toString(gasTh));//temperature


                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(AdminThresholds.this);
                    rQueue.add(request);
                }
                else
                {
                    Toast.makeText(AdminThresholds.this, "Temperature should be in [10, 40] ºC\nHumidity should be in [30, 80] %",Toast.LENGTH_LONG).show();
                }




            }
        });



    }



}
