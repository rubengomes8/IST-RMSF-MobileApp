package com.example.fire.Activities;

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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Android adding values using a button in ListView - Tutorial
// https://www.youtube.com/watch?v=ws_p8LJ4Uq8

public class AllDevicesAdmin extends AppCompatActivity {

    private User user;
    private ArrayList<Device> listDevices;
    private ArrayList<String> deviceList;
    private ArrayAdapter<String> adapter;
    ListView lv;
    private String URL;
    private int secret;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);


        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);
        username = intent.getStringExtra("username");

        getSupportActionBar().setTitle("Devices of " + username);

        lv = findViewById(R.id.listView);

        //deviceList = new ArrayList<Device>();
        deviceList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(AllDevicesAdmin.this, android.R.layout.simple_list_item_1, deviceList);

        lv.setAdapter(adapter);

        //Fazer Post para receber a lista de todos os devices de um certo user
        URL = "http://10.0.2.2:8000/admin/devices";

        //manda POST com secret e recebe 2 thresholds (humidity, temperature)

/*****************************************************************************************************************
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
                Toast.makeText(AllDevicesAdmin.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("secret", Integer.toString(secret));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(AllDevicesAdmin.this);
        rQueue.add(request);
*******************************************************************************************************************************/
        //Construir a lista com os items corretos. Cada item tem ID_device + Local
        deviceList.add("1 - Presentation Room");
        deviceList.add("2 - Meeting Room");
        deviceList.add("3 - Interviews Room");
        deviceList.add("4 - Open Space");
        deviceList.add("5 - Computers Room");
        deviceList.add("6 - CEO Room");
        deviceList.add("7 - WC Men");
        deviceList.add("8 - WC Women");
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String option = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(UserDevices.this, option, Toast.LENGTH_LONG).show();

                        //int id_device = getDeviceID(option, deviceList);
                        //String local = getDeviceLocal(option, deviceList);
                        int id_device = 1;
                        String local = "Presentation Room";

                        //Começa uma nova actividade onde envia o token e o username
                        Intent intent = new Intent(AllDevicesAdmin.this, AdminUserDeviceState.class);
                        intent.putExtra("id", id);
                        intent.putExtra("username", username);
                        intent.putExtra("local", local);
                        intent.putExtra("secret", secret);
                        startActivity(intent);



                    }
                }
        );
    }
}
