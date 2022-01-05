package com.example.serviciosandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
    }

    @Override
    public int getItemCount() {
        return estudiantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCedula, tvNombre, tvApellido, tvDireccion, tvTelefono;
        Context contexto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto = itemView.getContext();
            tvCedula = (TextView) itemView.findViewById(R.id.tvCedula);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellido = (TextView) itemView.findViewById(R.id.tvApellido);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            tvTelefono = (TextView) itemView.findViewById(R.id.tvTelefono);
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
