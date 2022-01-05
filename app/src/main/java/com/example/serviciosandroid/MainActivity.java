package com.example.serviciosandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvEstudiantes;
    AdaptadorEstudiante adaptadorEstudiante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvEstudiantes = (RecyclerView) findViewById(R.id.rvLista);
        rvEstudiantes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cargaDatosEstudiantes();
    }

    private void cargaDatosEstudiantes(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.10/soauta3/models/acceder.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<Estudiante> estudiantes = Estudiante.getEstudiantesFromJson(jsonArray);
                            adaptadorEstudiante = new AdaptadorEstudiante(estudiantes);
                            rvEstudiantes.setAdapter(adaptadorEstudiante);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(stringRequest);
    }
}