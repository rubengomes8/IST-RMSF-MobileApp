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

public class RemoveDevice extends AppCompatActivity {

    private int secret;
    private EditText eTId;
    private Button removeDevice;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_device);
        getSupportActionBar().setTitle("Remove device");

        Intent intent = getIntent();
        secret = intent.getIntExtra("secret", 0);

        eTId = findViewById(R.id.idDevBox);
        removeDevice = findViewById(R.id.removeDevButton);

        removeDevice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String aux = eTId.getText().toString();

                if(aux.equals(""))
                {
                    Toast.makeText(RemoveDevice.this, "Device id needed!", Toast.LENGTH_LONG);
                }
                else
                {
                    URL = "https://fire-240718.appspot.com/admin/deleteDevice/"; //URL de remover um device


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


                                    Intent intent1 = new Intent(RemoveDevice.this, MenuAdmin.class);
                                    intent1.putExtra("secret", secret);
                                    startActivity(intent1);

                                }
                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                }


                            }
                            else
                            {
                                //Do Nothing
                                Intent intent1 = new Intent(RemoveDevice.this, MenuAdmin.class);
                                intent1.putExtra("secret", secret);
                                startActivity(intent1);
                                Toast.makeText(RemoveDevice.this, "Device removed", Toast.LENGTH_LONG).show();


                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RemoveDevice.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("secret", Integer.toString(secret));
                            parameters.put("id", aux);
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(RemoveDevice.this);
                    rQueue.add(request);
                }
            }
        });


    }
}
