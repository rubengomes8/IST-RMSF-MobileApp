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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDevices extends AppCompatActivity {

    private User user;
    private String username;
    private ArrayList<String> listLocals;
    private ArrayList<Device> listDevices;
    private ArrayAdapter<String> adapter;
    ListView lv;
    private String URL;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_devices);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        lv = findViewById(R.id.listView);
        fab = findViewById(R.id.fab);

        getSupportActionBar().setTitle("Devices");
        //deviceList = new ArrayList<Device>();
        listLocals = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(UserDevices.this, android.R.layout.simple_list_item_1, listLocals);
        lv.setAdapter(adapter);

  /**************************************************************************************************************************************************
        //Fazer Post para receber a lista de todos os devices do user
        URL = "http://10.0.2.2:8000/app/devices";
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
                            listDevices.add(new Device(
                                    array.getJSONObject(i).getString("username"),
                                    array.getJSONObject(i).getString("token"),
                                    array.getJSONObject(i).getString("localization"),
                                    Integer.parseInt(array.getJSONObject(i).getString("id")))); //!!!Não sei muito bem se é este o nome do id ou se vem com _id

                            listLocals.add(array.getJSONObject(i).getString("localization"));
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
                Toast.makeText(UserDevices.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username);
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(UserDevices.this);
        rQueue.add(request);
**************************************************************************************************************************/
        //Construir a lista com os items corretos
        listLocals.add("Presentation Room");
        listLocals.add("Meeting Room");
        listLocals.add("Interviews Room");
        listLocals.add("Open Space");
        listLocals.add("Computers Room");
        listLocals.add("CEO Room");
        listLocals.add("WC Men");
        listLocals.add("WC Women");
        lv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String option = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(UserDevices.this, option, Toast.LENGTH_LONG).show();

                        //int token = getDeviceToken(option, listDevices);
                        int token = 2;
                        //Começa uma nova actividade onde envia o token e o username
                        Intent intent = new Intent(UserDevices.this, AdminUserDeviceState.class);
                        intent.putExtra("token", token);
                        intent.putExtra("username", username);
                        intent.putExtra("local", option);
                        startActivity(intent);



                    }
                }
        );

    }



}
