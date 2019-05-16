package com.example.fire.Activities;

import com.example.fire.models.User;
import com.pusher.pushnotifications.PushNotifications;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.fire.R;
import com.example.fire.models.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/************************************************** Objetivos *****************************************
 *
 * Sign Up e Login já estão
 * Admin já consegue alterar os valores dos thresholds
 *
 * Em falta:
 * Admin conseguir ver lista de todos os utilizadores e devices de cada utilizador
 * User conseguir ver o estado atual do seu device
 * User conseguir listar todos os seus devices
 * 
 *
 * Estava a fazer o AllDevices do admin
 *
 *
 *
 */

public class MainActivity extends AppCompatActivity {

    private EditText eTUsername;
    private EditText eTPassword;
    private Button login;
    private Button signup;
    private CheckBox checkBox;
    private String ipAddress = "127.0.0.1";
    private int secret = 2; //Não sei muito bem se deve ser private -> tenho de enviar sempre o secret numa operação de admin
    private String URL;
    private Integer check;
    private ArrayList<Device> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();
        PushNotifications.start(getApplicationContext(), "1a81f3fb-fa47-4820-9742-bf752a077700");
        //PushNotifications.addDeviceInterest("Leandro");
        PushNotifications.addDeviceInterest("CEOroom");
        PushNotifications.subscribe("hello");

        eTUsername = findViewById(R.id.usernameBox);
        eTPassword = findViewById(R.id.passwordBox);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);
        checkBox = findViewById(R.id.checkBoxAdmin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(checkBox.isChecked())
                {

                    /********************************************************************************************************

                     Log.d("checkbox", "Sou Admin");
                    URL = "http://10.0.2.2:8000/admin/"; //URL do admin


                    //manda 'secret' -1 se não tiver bem
                    //manda 'secret' diferente se vier bem > 0


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
                                    secret = object.getInt("secret");

                                    if(secret == -1)
                                    {
                                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                    else if(secret > 0)
                                    {
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                        //Começa nova atividade
                                        Intent intent1 = new Intent(MainActivity.this, MenuAdmin.class)
                                        intent1.putExtra("secret", secret);
                                        startActivty(intent1);
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
                            Toast.makeText(MainActivity.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("username", eTUsername.getText().toString());
                            parameters.put("password", eTPassword.getText().toString());
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);

                     *************************************************************************************************************/

                    //Começa nova atividade
                    Intent intent = new Intent(MainActivity.this, MenuAdmin.class);
                    intent.putExtra("secret", secret);
                    startActivity(intent);

                }
                else
                {
                    Log.d("checkbox", "Não sou Admin");

                    URL = "http://10.0.2.2:8000/app/"; //URL do user

                    //manda 0 se não existir user
                    //manda 1 se existir

                    //se tiver dispositivos vai fazer um post para pedir o array de dispositivos
/************************************************************************************************************************************
                    final StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response", "onResponse: STRING " + response);

                            if(!response.isEmpty())
                            {
                                try
                                {

                                    JSONObject object = new JSONObject(response);
                                    Log.d("object", "onResponse: JSON " + object);
                                    check = object.getInt("check");

                                    if(check == 0) //Não existe utilizador com esses dados
                                    {
                                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                    else if(check == 1) //Existe utilizador
                                    {
                                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();

                                        User user = new User(eTUsername.getText().toString(), eTPassword.getText().toString());

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
                            Toast.makeText(MainActivity.this, "Some error occurred: " + error, Toast.LENGTH_LONG).show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("email", eTUsername.getText().toString());
                            parameters.put("password", eTPassword.getText().toString());
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);

 *************************************************************************************************************/

                    Intent intent = new Intent(MainActivity.this, UserDevices.class);
                    intent.putExtra("username", eTUsername.getText().toString());
                    startActivity(intent);

                }



            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("signup", "Sign Up");
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);

            }
        });

    }
}
