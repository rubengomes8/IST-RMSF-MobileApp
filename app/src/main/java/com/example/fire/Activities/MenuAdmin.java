package com.example.fire.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fire.R;
import com.example.fire.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuAdmin extends AppCompatActivity {

    private String [] menuAdminOptions = {"Users", "Fires", "Thresholds", "All devices", "Remove user", "Remove device"};
    int secret;
    private int humidityTh;
    private int temperatureTh;
    private int carbonTh;
    private String URL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);


        getSupportActionBar().setTitle("Admin Menu");

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);

        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuAdminOptions);


        ListView menuListView = (ListView) findViewById(R.id.menuListView);
        menuListView.setAdapter(menuAdapter);

        menuListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String option = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(MenuAdmin.this, option, Toast.LENGTH_LONG).show();

                        if(option.equals("Users"))
                        {
                            //Fazer um POST com o secret e receber um JSONArray com todos os utilizadores
                            //Abrir actividade onde pode ver o valor dos thresholds de humidade e temperatura e pode alterá-los
                            Intent intent2 = new Intent(MenuAdmin.this, UserList.class);
                            intent2.putExtra("secret", secret);
                            startActivity(intent2);

                        }
                        else if(option.equals("Fires"))
                        {
                            //Fazer um POST com o secret e receber um JSONArray com todos os devices
                            Intent intent2 = new Intent(MenuAdmin.this, AllFiresAdmin.class);
                            intent2.putExtra("secret", secret);
                            startActivity(intent2);
                        }

                        else if(option.equals("All devices"))
                        {
                            //Fazer um POST com o secret e receber um JSONArray com todos os devices
                            Intent intent2 = new Intent(MenuAdmin.this, AllDevicesAdmin.class);
                            intent2.putExtra("secret", secret);
                            startActivity(intent2);
                        }

                        else if(option.equals("Thresholds"))
                        {

                            //Fazer Post para receber os valores dos thresholds
                            Log.d("checkbox", "Não sou Admin");
                            URL = "https://fire-240718.appspot.com/admin/thresholds/"; //

                            //manda POST com secret e recebe 2 thresholds (humidity, temperature)


                            final StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", "onResponse: STRING " + response);

                                    if(!response.isEmpty())
                                    {
                                        try
                                        {

                                            JSONObject object = new JSONObject(response);
                                            Log.d("object", "onResponse: JSON " + object);
                                            humidityTh = object.getInt("humidity");
                                            temperatureTh = object.getInt("temperature");
                                            carbonTh = object.getInt("gas");
                                            //Abrir actividade onde pode ver o valor dos thresholds de humidade e temperatura e pode alterá-los
                                            Intent intent2 = new Intent(MenuAdmin.this, AdminThresholds.class);
                                            intent2.putExtra("secret", secret);
                                            intent2.putExtra("hTh", humidityTh);
                                            intent2.putExtra("tTh", temperatureTh);
                                            intent2.putExtra("cTh", carbonTh);
                                            Log.d("th_tag", Integer.toString(humidityTh));
                                            Log.d("th_tag", Integer.toString(temperatureTh));
                                            Log.d("th_tag", Integer.toString(carbonTh));
                                            startActivity(intent2);

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
                                    Toast.makeText(MenuAdmin.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<String, String>();

                                    Log.d("secretC", Integer.toString(secret));
                                    //parameters.put("secret", Integer.toString(secret));
                                    return parameters;
                                }
                            };

                            RequestQueue rQueue = Volley.newRequestQueue(MenuAdmin.this);
                            rQueue.add(request);

                        }
                        else if(option.equals("Remove user"))
                        {
                            Intent intent2 = new Intent(MenuAdmin.this, RemoveUser.class);
                            intent2.putExtra("secret", secret);
                            startActivity(intent2);
                        }
                        else if(option.equals("Remove device"))
                        {
                            Intent intent2 = new Intent(MenuAdmin.this, RemoveDevice.class);
                            intent2.putExtra("secret", secret);
                            startActivity(intent2);
                        }

                    }
                }
        );


    }


}
