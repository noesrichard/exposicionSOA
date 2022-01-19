package com.example.serviciosandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inicio extends AppCompatActivity {

    Button btnIniciar;
    Context contexto;
    TextView tvIniciar;
    SharedPreferences preferences;
    EditText etUsername, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("com.example.serviciosandroid", Context.MODE_PRIVATE);
        if(preferences.contains("session")){
           siguiente();
        }else {
            setContentView(R.layout.activity_inicio);
            contexto = getApplicationContext();
            tvIniciar = (TextView) findViewById(R.id.tvIniciarSesion);
            btnIniciar = (Button) findViewById(R.id.btnIniciar);
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btnIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login(etUsername.getText().toString(), etPassword.getText().toString());
                }
            });
        }
    }



    public void siguiente(){
        preferences.edit().putString("session","true").commit();
        Intent intent = new Intent(Inicio.this,MainActivity.class);
        this.startActivity(intent);
    }
    public void login(String username, String password){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.10/soauta3/models/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("200")) {
                            siguiente();
                            etUsername.setText("");
                            etPassword.setText("");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
                };

        queue.add(stringRequest);
    }
}