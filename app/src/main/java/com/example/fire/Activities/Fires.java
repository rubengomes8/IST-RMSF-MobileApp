package com.example.fire.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fires extends AppCompatActivity {

    private String username;
    private String password;
    private int token;
    ListView lv;
    private ArrayList<String> fires = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String URL;

    private HashMap<String,String> positionDescription = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fires);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Fires");

        //getSupportActionBar().setTitle("Fires");
        lv = findViewById(R.id.listViewFires);
        adapter = new ArrayAdapter<String>(Fires.this, android.R.layout.simple_list_item_1, fires);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        token = intent.getIntExtra("token", 0);



        URL = "https://fire-240718.appspot.com/app/fires/";
        //manda POST com username e recebe os seus devices
        final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", "onResponse: STRING " + response);

                if(!response.isEmpty())
                {
                    Log.d("debug", "ola");
                    try
                    {
                        JSONArray array = new JSONArray(response);
                        Log.d("array", array.toString());

                        for (int i= 0; i< array.length(); i++){
                            JSONObject aux = new JSONObject(array.getJSONObject(i).getString("fields"));
                            Log.d("fields", aux.getString("date"));
                            Log.d("fields", aux.getString("device"));
                            Log.d("fields", aux.getString("description"));
                            String pk = array.getJSONObject(i).getString("pk");
                            Log.d("fields", "pk " + pk);
                            String subDate = aux.getString("date").substring(0,10);
                            fires.add("Data: " + subDate + "                    " + "Device ID: " + aux.getString("device"));
                            positionDescription.put(Integer.toString(i), aux.getString("description") + " " + Integer.toString(i));

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
                Toast.makeText(Fires.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username);
                parameters.put("password", password);
                //Log.d("token", Integer.toString(token));
                parameters.put("token", Integer.toString(token));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(Fires.this);
        rQueue.add(request);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = positionDescription.get(Integer.toString(position));
                Snackbar.make(view, "Descrição: " + description, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
