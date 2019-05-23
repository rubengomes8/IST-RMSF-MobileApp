package com.example.fire.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class SignUp extends AppCompatActivity {


    private Button create;
    private EditText eTUsername;
    private EditText eTPassword1;
    private EditText eTPassword2;
    private String password1;
    private String password2;
    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");

        eTUsername = findViewById(R.id.usernameBox);
        eTPassword1 = findViewById(R.id.passwordBox1);
        eTPassword2 = findViewById(R.id.passwordBox2);
        create = findViewById(R.id.createButton);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Verifica se as passwords coincidem
                //Se coincidirem, faz post de {username, password}
                //Recebe check=0 se ja existir esse username
                //Recebe check=1 se sucedeu
                password1 = eTPassword1.getText().toString();
                password2 = eTPassword2.getText().toString();

                if(password1.equals(password2))
                {
                    //post de {username, password}
                    URL = "https://fire-240718.appspot.com/app/signup/"; //URL do admin

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
                                        Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_LONG).show();
                                    }
                                    else if(check == 1)
                                    {
                                        Toast.makeText(SignUp.this, "Account created successfully", Toast.LENGTH_LONG).show();
                                        //Talvez fazer uma mudança de atividade para a Atividade de Login?
                                        Intent intent2 =  new Intent(SignUp.this, MainActivity.class);
                                        startActivity(intent2);
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
                            Toast.makeText(SignUp.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("username", eTUsername.getText().toString());
                            parameters.put("password", eTPassword1.getText().toString());
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(SignUp.this);
                    rQueue.add(request);

                }
                else
                {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                }



            }
        });
    }

}
