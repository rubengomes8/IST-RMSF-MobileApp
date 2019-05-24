package com.example.fire.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fire.R;
import com.example.fire.models.Device;
import com.example.fire.models.User;
import com.pusher.pushnotifications.PushNotifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

/* user */
public class UserDevices extends AppCompatActivity {

    private User user;
    private String username;
    private int secret;
    private String password;
    private ArrayList<String> listLocals;
    private ArrayList<Device> listDevices;
    private ArrayAdapter<String> adapter;
    private HashMap<String,Integer> devSet;
    ListView lv;
    private String URL;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_devices);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        secret = intent.getIntExtra("secret", 0);


        lv = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);

        if(secret > 0) //Admin -> retirar o float action button para adicionar 1 device
        {
            fab.hide();
        }

        getSupportActionBar().setTitle("Devices of "+username);
        //deviceList = new ArrayList<Device>();
        listLocals = new ArrayList<String>();
        devSet = new HashMap<>();

        adapter = new ArrayAdapter<String>(UserDevices.this, android.R.layout.simple_list_item_1, listLocals);
        //lv.setAdapter(adapter);

        //Fazer Post para receber a lista de todos os devices do user
        URL = "https://fire-240718.appspot.com/app/devices/";
        //manda POST com username e recebe os seus devices
        final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", "onResponse: STRING " + response);

                if(!response.isEmpty())
                {
                    try
                    {
                        JSONArray array = new JSONArray(response);

                        //processa cada objeto e cria uma lista de Strings ou de devices e faz set do adapter
                        for (int i= 0; i< array.length(); i++){
                            JSONObject aux = new JSONObject(array.getJSONObject(i).getString("fields"));
                            String dev_id = array.getJSONObject(i).getString("pk");
                            PushNotifications.addDeviceInterest(dev_id);
                            devSet.put(aux.getString("localization"),Integer.parseInt(aux.getString("token")));

                            listLocals.add(aux.getString("localization"));
                        }

                        lv.setAdapter(adapter);


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
                Toast.makeText(UserDevices.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username);
                parameters.put("password", password);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(UserDevices.this);
        rQueue.add(request);
        //Construir a lista com os items corretos






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a new device 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(UserDevices.this, AddDevice.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);

            }
        });


        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //Toast.makeText(UserDevices.this, option, Toast.LENGTH_LONG).show();

                        String option = String.valueOf(parent.getItemAtPosition(position));
                        int token = devSet.get(option);

                        //Começa uma nova actividade onde envia o token e o username
                        Intent intent = new Intent(UserDevices.this, DeviceState.class);
                        intent.putExtra("token", token);
                        intent.putExtra("secret", secret);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        intent.putExtra("local", option);
                        startActivity(intent);



                    }
                }
        );

    }



}
