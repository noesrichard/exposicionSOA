package com.example.serviciosandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdaptadorEstudiante extends RecyclerView.Adapter<AdaptadorEstudiante.ViewHolder>{

    ArrayList<Estudiante> estudiantes;

    public AdaptadorEstudiante(ArrayList<Estudiante> estudiantes){
        this.estudiantes = estudiantes;
    }

    @NonNull
    @Override
    public AdaptadorEstudiante.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carta_estudiante, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEstudiante.ViewHolder holder, int position) {
        holder.asignarDatos(estudiantes.get(position));
        holder.accionEditarEstudiante(estudiantes.get(position));
        holder.accionEliminarEstudiante(estudiantes.get(position), estudiantes);
    }

    @Override
    public int getItemCount() {
        return estudiantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCedula, tvNombre, tvApellido, tvDireccion, tvTelefono;
        Context contexto;
        Button btnEditar, btnEliminar;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            contexto = itemView.getContext();
            tvCedula = (TextView) itemView.findViewById(R.id.tvCedula);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellido = (TextView) itemView.findViewById(R.id.tvApellido);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            tvTelefono = (TextView) itemView.findViewById(R.id.tvTelefono);
            btnEditar = (Button) itemView.findViewById(R.id.btnEditar);
            btnEliminar = (Button) itemView.findViewById(R.id.btnEliminar);
        }

        public void accionEliminarEstudiante(Estudiante e, ArrayList<Estudiante> estudiantes){
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                    builder.setTitle("Eliminar Estudiante");
                    builder.setMessage("Esta seguro que desea eliminar al estudiante con cedula: "+e.getCedula()+"?");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eliminarEstudiante(e, estudiantes);
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
            });
        }

        public void accionEditarEstudiante(Estudiante e){
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarInterfaz(e);
                }
            });
        }

        public void cargarInterfaz(Estudiante e){
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            View inflater = LayoutInflater.from(contexto).inflate(R.layout.crear_estudiante, null);
            builder.setTitle("Editar Estudiante");
            EditText etCedula = inflater.findViewById(R.id.etCedula);
            EditText etNombre = inflater.findViewById(R.id.etNombre);
            EditText etApellido = inflater.findViewById(R.id.etApellido);
            EditText etDireccion = inflater.findViewById(R.id.etDireccion);
            EditText etTelefono = inflater.findViewById(R.id.etTelefono);

            etCedula.setText(e.getCedula());
            etNombre.setText(e.getNombre());
            etApellido.setText(e.getApellido());
            etDireccion.setText(e.getDireccion());
            etTelefono.setText(e.getTelefono());

            etCedula.setEnabled(false);

            builder.setView(inflater);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editarEstudiante(etCedula.getText().toString(),
                            etNombre.getText().toString(),etApellido.getText().toString(),
                            etDireccion.getText().toString(),etTelefono.getText().toString(), e);
                }
            });
            builder.show();
        }
        public void eliminarEstudiante(Estudiante e, ArrayList<Estudiante> estudiantes){
            RequestQueue queue = Volley.newRequestQueue(contexto);
            String url = "http://192.168.0.10/soauta3/models/eliminar.php";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            estudiantes.remove(e);
                            notifyDataSetChanged();
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
                    params.put("EST_CEDULA", e.getCedula());
                    return params;
                }
            };
            queue.add(request);
        }


        public void editarEstudiante(String cedula, String nombre, String apellido,
                                    String direccion, String telefono, Estudiante e){
            RequestQueue queue = Volley.newRequestQueue(contexto);
            String url = "http://192.168.0.10/soauta3/models/modificar.php";
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            e.setCedula(cedula); e.setApellido(apellido); e.setNombre(nombre);
                            e.setDireccion(direccion);
                            e.setTelefono(telefono);
                            asignarDatos(e);
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
        public void asignarDatos(Estudiante estudiante){
            tvCedula.setText(estudiante.getCedula());
            tvNombre.setText(estudiante.getNombre());
            tvApellido.setText(estudiante.getApellido());
            tvDireccion.setText(estudiante.getDireccion());
            tvTelefono.setText(estudiante.getTelefono());
        }
    }
}
