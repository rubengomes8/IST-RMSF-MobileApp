package com.example.fire.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class AddDevice extends AppCompatActivity {

    private EditText eTToken;
    private EditText eTLocal;
    private String token;
    private String local;
    private Button addDevice;
    private String URL;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        getSupportActionBar().setTitle("New Device");

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        eTToken = findViewById(R.id.tokenBox);
        eTLocal = findViewById(R.id.localBox);
        addDevice = findViewById(R.id.addButton);

        addDevice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                URL = "https://fire-240718.appspot.com/app/updateDevice/"; //URL para adicionar um novo device

                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "onResponse: STRING " + response);

                        if(!response.isEmpty())
                        {
                            try
                            {

                                JSONObject object = new JSONObject(response);
                                Log.d("object", "onResponse: JSON " + object);
                                int check = object.getInt("check");
                                if (check == 0)
                                {
                                    Toast.makeText(AddDevice.this, "Incorrect token", Toast.LENGTH_LONG).show();
                                }
                                else if(check == 1)
                                {
                                    Toast.makeText(AddDevice.this, "New device added successfully", Toast.LENGTH_LONG).show();
                                    //Talvez fazer uma mudança de atividade para a Atividade de Login?
                                    Intent intent2 =  new Intent(AddDevice.this, UserDevices.class);
                                    intent2.putExtra("username", username);
                                    intent2.putExtra("password", password);
                                    startActivity(intent2);
                                }



                            }
                            catch(JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddDevice.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("username", username);
                        parameters.put("password", password);
                        parameters.put("token", eTToken.getText().toString());
                        parameters.put("localization", eTLocal.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(AddDevice.this);
                rQueue.add(request);

            }
        });
    }
}
