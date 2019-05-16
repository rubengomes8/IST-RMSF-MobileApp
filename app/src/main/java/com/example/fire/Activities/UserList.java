package com.example.fire.Activities;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserList extends AppCompatActivity {

    private User user;
    private ArrayList<String> listUsernames;
    private ArrayList<User> listUsers;
    private ArrayAdapter<String> adapter;
    ListView lv;
    private String URL;
    private int secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);

        lv = findViewById(R.id.listViewUsers);
        listUsernames = new ArrayList<String>();
        listUsers = new ArrayList<User>();
        adapter = new ArrayAdapter<String>(UserList.this, android.R.layout.simple_list_item_1, listUsernames);

        lv.setAdapter(adapter);
/********************************************************************************************************************
        //Fazer Post para receber a lista de todos os users
        URL = "http://10.0.2.2:8000/admin/users";


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
                            listUsers.add(new User(array.getJSONObject(i).getString("username"), array.getJSONObject(i).getString("password")));
                            listUsernames.add(array.getJSONObject(i).getString("username"));
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
                Toast.makeText(UserList.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("secret", Integer.toString(secret));
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(UserList.this);
        rQueue.add(request);
 **********************************************************************************************************/

        //Construir a lista com os items corretos
        listUsernames.add("fire_user");
        listUsernames.add("sigfox_founder");
        listUsernames.add("lora_founder");
        listUsernames.add("securitas_direct");
        listUsernames.add("extin_risco");
        listUsernames.add("exportech");
        listUsernames.add("global_fire");

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String username = String.valueOf(parent.getItemAtPosition(position));
                        //Toast.makeText(UserDevices.this, option, Toast.LENGTH_LONG).show();


                        //Opção de remover utilizador???

                        //Faz post do username do utilizador e recebe a lista de devices desse utilizador e começa uma nova atividade dos devices desse user
                        Intent intent1 = new Intent(UserList.this, AllDevicesAdmin.class);
                        intent1.putExtra("secret", secret);
                        intent1.putExtra("username", username);
                        startActivity(intent1);





                    }
                }
        );
    }
}
