package com.example.fire.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddDescription extends AppCompatActivity {

    private int secret;
    private EditText eTDesc;
    private Button addDescB;
    private String date;
    private String URL;
    private int pk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);

        eTDesc = findViewById(R.id.descBox);
        addDescB = findViewById(R.id.addDescButton);

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);
        date = intent.getStringExtra("date");
        pk = Integer.parseInt(intent.getStringExtra("pk"));


        getSupportActionBar().setTitle("Fire occured at: " + date);


        addDescB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                URL = "https://fire-240718.appspot.com/admin/fires/"; //URL de adicionar descrição


                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "onResponse: STRING " + response);

                        if(!response.isEmpty())
                        {

                            Log.d("Estou aqui", "olaola");
                            Intent intent1 = new Intent(AddDescription.this, MenuAdmin.class);
                            intent1.putExtra("secret", secret);
                            startActivity(intent1);
                            Toast.makeText(AddDescription.this, "Fire description added successfully", Toast.LENGTH_LONG).show();


                        }
                        else
                        {
                            //Do Nothing
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddDescription.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();

                        Log.d("secretA", Integer.toString(secret));
                        parameters.put("secret", Integer.toString(secret));
                        Log.d("secretA", eTDesc.getText().toString());
                        parameters.put("description", eTDesc.getText().toString());
                        ;
                        Log.d("secretA", Integer.toString(pk));
                        parameters.put("id", Integer.toString(pk));
                        return parameters;
                    }
                };

                RequestQueue rQueue = Volley.newRequestQueue(AddDescription.this);
                rQueue.add(request);

            }
        });

    }
}
