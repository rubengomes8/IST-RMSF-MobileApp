package com.example.fire.Activities;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllFiresAdmin extends AppCompatActivity {

    private int secret;
    ListView lv;
    private ArrayList<String> fires = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String URL;
    private HashMap<String,String> positionDescription = new HashMap<>();
    private HashMap<String,String> positionDate = new HashMap<>();
    private HashMap<String,String> positionID = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_fires_admin);

        getSupportActionBar().setTitle("All Fires");

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);

        lv = findViewById(R.id.allFiresListView);
        adapter = new ArrayAdapter<String>(AllFiresAdmin.this, android.R.layout.simple_list_item_1, fires);

        URL = "https://fire-240718.appspot.com/admin/allFires/";
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
                            Log.d("fields", aux.getString("date"));
                            Log.d("fields", aux.getString("device"));
                            Log.d("fields", aux.getString("description"));
                            String pk = array.getJSONObject(i).getString("pk");
                            Log.d("fields", "pk " + pk);
                            String subDate = aux.getString("date").substring(0,10);
                            fires.add("Data: " + subDate + "                    " + "Device ID: " + aux.getString("device"));
                            positionDescription.put(Integer.toString(i), aux.getString("description") + " " + Integer.toString(i));
                            positionDate.put(Integer.toString(i), subDate);
                            positionID.put(Integer.toString(i), pk);

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
                Toast.makeText(AllFiresAdmin.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                Log.d("secretB", Integer.toString(secret));
                parameters.put("secret", Integer.toString(secret));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(AllFiresAdmin.this);
        rQueue.add(request);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = positionDescription.get(Integer.toString(position));
                String date = positionDate.get(Integer.toString(position));
                //Só pode alterar sse não tiver sido ja alterada a descrição

                if(description.length() > 10)
                {
                    if(description.substring(0,10).equals("Admin did "))
                    {
                        Intent intent1 = new Intent(AllFiresAdmin.this, AddDescription.class);
                        intent1.putExtra("secret", secret);
                        intent1.putExtra("date", date);
                        intent1.putExtra("pk", positionID.get(Integer.toString(position)));
                        startActivity(intent1);
                    }
                    else
                    {
                        Snackbar.make(view, "Descrição: " + description, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                else
                {
                    Snackbar.make(view, "Descrição: " + description, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }



            }
        });

    }
}
