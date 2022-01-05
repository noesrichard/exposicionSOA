package com.example.serviciosandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView rvEstudiantes;
    AdaptadorEstudiante adaptadorEstudiante;
    Button btnCrear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvEstudiantes = (RecyclerView) findViewById(R.id.rvLista);
        rvEstudiantes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        btnCrear = findViewById(R.id.btnCrear);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarInterfaz();
            }
        });
        cargaDatosEstudiantes();
    }

    public void cargarInterfaz(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View inflater = LayoutInflater.from(MainActivity.this).inflate(R.layout.crear_estudiante, null);
        builder.setTitle("Crear Estudiante");
        EditText etCedula = inflater.findViewById(R.id.etCedula);
        EditText etNombre = inflater.findViewById(R.id.etNombre);
        EditText etApellido = inflater.findViewById(R.id.etApellido);
        EditText etDireccion = inflater.findViewById(R.id.etDireccion);
        EditText etTelefono = inflater.findViewById(R.id.etTelefono);
        builder.setView(inflater);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                crearEstudiante(etCedula.getText().toString(),
                        etNombre.getText().toString(),etApellido.getText().toString(),
                        etDireccion.getText().toString(),etTelefono.getText().toString());
            }
        });
        builder.show();
    }

    public void crearEstudiante(String cedula, String nombre, String apellido,
                                String direccion, String telefono){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.10/soauta3/models/guardar.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cargaDatosEstudiantes();
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
                params.put("EST_CEDULA", cedula);
                params.put("EST_NOMBRE", nombre);
                params.put("EST_APELLIDO", apellido);
                params.put("EST_DIRECCION", direccion);
                params.put("EST_TELEFONO", telefono);
                return params;
            }
        };
        queue.add(request);
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